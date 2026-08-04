package com.brainflow.brainflow.dto.response;

public class DashboardMetricsResponseDTO {

    private long sessionsCount;
    private long activeUsersCount;
    private int validatedSessionsRate;
    private int openIssuesCount;

    public DashboardMetricsResponseDTO() {
    }

    public DashboardMetricsResponseDTO(long sessionsCount, long activeUsersCount, int validatedSessionsRate, int openIssuesCount) {
        this.sessionsCount = sessionsCount;
        this.activeUsersCount = activeUsersCount;
        this.validatedSessionsRate = validatedSessionsRate;
        this.openIssuesCount = openIssuesCount;
    }

    public long getSessionsCount() {
        return sessionsCount;
    }

    public void setSessionsCount(long sessionsCount) {
        this.sessionsCount = sessionsCount;
    }

    public long getActiveUsersCount() {
        return activeUsersCount;
    }

    public void setActiveUsersCount(long activeUsersCount) {
        this.activeUsersCount = activeUsersCount;
    }

    public int getValidatedSessionsRate() {
        return validatedSessionsRate;
    }

    public void setValidatedSessionsRate(int validatedSessionsRate) {
        this.validatedSessionsRate = validatedSessionsRate;
    }

    public int getOpenIssuesCount() {
        return openIssuesCount;
    }

    public void setOpenIssuesCount(int openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }
}
