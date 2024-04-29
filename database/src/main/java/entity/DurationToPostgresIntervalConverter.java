package entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

@Converter(autoApply = true)
public class DurationToPostgresIntervalConverter implements AttributeConverter<Duration, String> {

    @Override
    public String convertToDatabaseColumn(Duration attribute) {
        if (attribute == null) {
            return null;
        }
        // The format of interval for Postgres should be 'HH:MM:SS'
        // For example, '1 hour' will be '01:00:00'
        long hours = attribute.toHours();
        long minutes = attribute.toMinutesPart();
        long seconds = attribute.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public Duration convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        // Your existing conversion logic to convert a string back to Duration
        // Ensure it matches the string format we use for PostgreSQL
        String[] parts = dbData.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);
        return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }
}
