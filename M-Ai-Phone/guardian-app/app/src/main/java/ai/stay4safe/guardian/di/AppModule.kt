package ai.stay4safe.guardian.di

import ai.stay4safe.guardian.service.AuditLogService
import ai.stay4safe.guardian.service.PrivacyMonitorService
import ai.stay4safe.guardian.service.ScamCallService
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt DI Module — Stay4S Guardian
 * Alle services en repositories worden hier als singletons geregistreerd
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuditLogService(
        @ApplicationContext context: Context
    ): AuditLogService = AuditLogService(context)

    @Provides
    @Singleton
    fun provideScamCallService(
        @ApplicationContext context: Context,
        auditLog: AuditLogService
    ): ScamCallService = ScamCallService(context, auditLog)

    @Provides
    @Singleton
    fun providePrivacyMonitorService(
        @ApplicationContext context: Context,
        auditLog: AuditLogService
    ): PrivacyMonitorService = PrivacyMonitorService(context, auditLog)
}
