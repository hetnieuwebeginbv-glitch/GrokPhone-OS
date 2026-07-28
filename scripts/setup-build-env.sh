#!/bin/bash
# setup-build-env.sh — Stay4OS build environment setup for Ubuntu 24.04
# Run this once on a fresh build server

set -euo pipefail

echo "=== Stay4OS Build Environment Setup ==="

# 1. System packages
sudo apt update && sudo apt install -y \
    android-sdk-libsparse-utils \
    android-sdk-ext4-utils \
    bc bison build-essential ccache \
    curl flex g++-multilib gcc-multilib \
    git git-lfs gnupg gperf imagemagick \
    lib32ncurses5-dev lib32readline-dev \
    lib32z1-dev libelf-dev liblz4-tool \
    libncurses5 libncurses5-dev \
    libsdl1.2-dev libssl-dev libxml2 \
    libxml2-utils lz4 lzop pngcrush \
    python3 python3-pip python-is-python3 \
    rsync schedtool squashfs-tools \
    xsltproc zip zlib1g-dev

# 2. OpenJDK 17
sudo apt install -y openjdk-17-jdk
java -version

# 3. repo tool
if ! command -v repo &> /dev/null; then
    mkdir -p ~/.local/bin
    curl -sS https://storage.googleapis.com/git-repo-downloads/repo > ~/.local/bin/repo
    chmod a+x ~/.local/bin/repo
    export PATH="$HOME/.local/bin:$PATH"
    echo 'export PATH="$HOME/.local/bin:$PATH"' >> ~/.bashrc
fi

# 4. Configure ccache
echo 'export USE_CCACHE=1' >> ~/.bashrc
echo 'export CCACHE_DIR=/mnt/ccache' >> ~/.bashrc
echo 'export CCACHE_COMPRESS=1' >> ~/.bashrc
mkdir -p /mnt/ccache
ccache -M 50G

# 5. Git config
git config --global user.name "Stay4S Build Bot"
git config --global user.email "build@stay4s.ai"
git config --global color.ui auto

# 6. Create build directory
mkdir -p ~/stay4os

echo ""
echo "=== Setup complete ==="
echo "Next steps:"
echo "  1. cd ~/stay4os"
echo "  2. repo init -u https://github.com/LineageOS/android.git -b lineage-23.2"
echo "  3. cp stay4s-saip/scripts/local_manifests/asteroids.xml .repo/local_manifests/"
echo "  4. repo sync -j$(nproc)"
echo "  5. # Extract vendor blobs"
echo "  6. source build/envsetup.sh && lunch lineage_asteroids-userdebug"
echo "  7. mka bacon -j$(nproc)"
