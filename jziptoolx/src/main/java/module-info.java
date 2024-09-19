module jziptoolx {
    exports com.andymememe.jziptoolx.treer;
    exports com.andymememe.jziptoolx.zipper;
    exports com.andymememe.jziptoolx;

    requires java.desktop;
    requires java.logging;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires jtar;

    opens com.andymememe.jziptoolx to javafx.fxml, javafx.graphics;
}
