import Presenter.ToyPresenter;
import View.ConsoleView;

public class ChildrenGoodsStore {
    /**
     * The main entry point of the program.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        ToyPresenter toyPresenter = new ToyPresenter();
        ConsoleView consoleView = new ConsoleView(toyPresenter);

        consoleView.run();
    }
}
