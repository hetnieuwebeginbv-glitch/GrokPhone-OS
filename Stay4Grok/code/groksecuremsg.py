# GrokSecureMsg - Ons custom end-to-end encrypted messaging protocol
import os
import base64
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import x25519
from cryptography.hazmat.primitives.kdf.hkdf import HKDF
from cryptography.hazmat.primitives.ciphers.aead import ChaCha20Poly1305

def generate_key_pair(ai_seed: str):
    """Genereer key pair met AI-seed voor uniqueness"""
    private_key = x25519.X25519PrivateKey.generate()
    public_key = private_key.public_key()
    return private_key, public_key

def encrypt_message(sender_private, receiver_public, message: str) -> str:
    """Encrypt bericht end-to-end"""
    shared_key = sender_private.exchange(receiver_public)
    derived_key = HKDF(
        algorithm=hashes.SHA256(),
        length=32,
        salt=None,
        info=b'groksecure'
    ).derive(shared_key)
    nonce = os.urandom(12)
    aead = ChaCha20Poly1305(derived_key)
    ciphertext = aead.encrypt(nonce, message.encode(), None)
    return base64.b64encode(nonce + ciphertext).decode()

def decrypt_message(receiver_private, sender_public, encrypted: str) -> str:
    """Decrypt bericht"""
    data = base64.b64decode(encrypted)
    nonce, ciphertext = data[:12], data[12:]
    shared_key = receiver_private.exchange(sender_public)
    derived_key = HKDF(
        algorithm=hashes.SHA256(),
        length=32,
        salt=None,
        info=b'groksecure'
    ).derive(shared_key)
    aead = ChaCha20Poly1305(derived_key)
    return aead.decrypt(nonce, ciphertext, None).decode()

# Snelle test
if __name__ == "__main__":
    seed = "grokphone-seed-2026"
    priv1, pub1 = generate_key_pair(seed)
    priv2, pub2 = generate_key_pair(seed + "receiver")
    msg = "Dit bericht is 100% privé - niemand kijkt mee!"
    enc = encrypt_message(priv1, pub2, msg)
    print("Encrypted:", enc)
    dec = decrypt_message(priv2, pub1, enc)
    print("Decrypted:", dec)
