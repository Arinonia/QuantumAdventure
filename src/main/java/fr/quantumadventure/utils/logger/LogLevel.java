package fr.quantumadventure.utils.logger;

public enum LogLevel {
    INFO("INFOS  "),
    WARN("WARNING"),
    ERROR("ERROR  "),
    DEBUG("DEBUG  "),
    SUCCESS("SUCCESS")
    ;
    private final String prefix;

    LogLevel(final String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }
}
