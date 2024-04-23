package sg.edu.ntu.sc2002;

/** View menu of a particular branch. */
public class ViewMenuAction {
    /**
     * View menu of a Fast Food Branch.
     *
     * @param branch The branch which the menu belongs to.
     */
    public static void viewMenu(Branch branch) {
        System.out.println("-------------------------");
        int i = 1;
        for (Item item : branch.getSortedMenu()) {
            System.out.println(
                    String.format(
                            "%d) Name: %s, Category: %s, Price: %.2f, Description: %s",
                            i++,
                            item.getName(),
                            item.getCategory(),
                            item.getPrice(),
                            item.getDescription()));
        }
    }
}
