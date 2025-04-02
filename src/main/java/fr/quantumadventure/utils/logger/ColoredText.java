package fr.quantumadventure.utils.logger;

public class ColoredText {
    private final String text;
    private final String color;

    public ColoredText(final String text, final String color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return this.text;
    }

    public String getColor() {
        return this.color;
    }
}
