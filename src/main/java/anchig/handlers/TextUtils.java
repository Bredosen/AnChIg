package anchig.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;

public final class TextUtils {

    public final static TextComponent top(final String title) {
        TextComponent text = Component.text("===============").color(ColorScheme.border);
        text = text.append(Component.text(title).color(ColorScheme.title).decorate(TextDecoration.BOLD));
        text = text.append(Component.text("===============").color(ColorScheme.border));
        return text;
    }

    public final static TextComponent value(final String text1, final Object value, final String text2) {
        TextComponent text = Component.text(text1).color(ColorScheme.text);
        text = text.append(Component.text(value.toString()).color(ColorScheme.value).decorate(TextDecoration.BOLD));
        text = text.append(Component.text(text2).color(ColorScheme.text));
        return text;
    }

    public final static TextComponent error(final String error) {
        TextComponent text = Component.text(error).color(ColorScheme.error).decorate(TextDecoration.ITALIC);
        return text;
    }

    public final static TextComponent marker(final String marker, final String text1) {
        TextComponent text = Component.text(marker).color(ColorScheme.marker);
        text = text.append(Component.text(text1).color(ColorScheme.text));
        return text;
    }

    public final static TextComponent onlyPlayer() {
        TextComponent text = Component.text("Sorry, but only a player can execute this command!").color(ColorScheme.error);
        return text;
    }
}
