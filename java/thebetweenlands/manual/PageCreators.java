package thebetweenlands.manual;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import thebetweenlands.manual.widgets.*;
import thebetweenlands.manual.widgets.text.TextContainer;
import thebetweenlands.manual.widgets.text.TextFormatComponents;
import thebetweenlands.manual.widgets.text.TextWidget;

import java.util.ArrayList;

/**
 * Created by Bart on 23/11/2015.
 */
public class PageCreators {

    public static int craftingRecipeHeight = CraftingRecipeWidget.height + 5;
    public static int smeltingRecipeHeight = SmeltingRecipeWidget.height + 5;
    public static int compostRecipeHeight = CompostRecipeWidget.height + 5;
    public static int pestleAndMortarRecipeHeight = PestleAndMortarRecipeWidget.height + 5;
    public static int purifierRecipeHeight = PurifierRecipeWidget.height + 5;
    public static int rubberTabRecipeHeight = RubberTabWidget.height + 5;

    public static ArrayList<Page> pageCreatorButtons(ArrayList<Page> pages) {
        ArrayList<Page> newPages = new ArrayList<>();
        int pageAmount = pages.size();
        int times = 0;
        while (pageAmount > 0) {
            ArrayList<Page> pagesTemp = new ArrayList<>();
            pagesTemp.addAll(pages.subList(8 * times, (pageAmount > 8 ? 8 + 8 * times : 8 * times + pageAmount)));
            int height = 0;
            ArrayList<ManualWidgetsBase> widgets = new ArrayList<>();
            for (Page page : pagesTemp) {
                widgets.add(new ButtonWidget(15, 10 + height, page));
                height += 18;
            }
            newPages.add(new Page("index" + times, (ArrayList<ManualWidgetsBase>) widgets.clone()));
            widgets.clear();
            pagesTemp.clear();
            pageAmount -= 8;
            times++;
        }

        return newPages;
    }

    public static ArrayList<Page> pageCreatorEntities(IManualEntryEntity entity, boolean isHidden) {
        ArrayList<Page> newPages = new ArrayList<>();
        String title = entity.manualName();
        newPages.add(new Page(title, isHidden, new TextWidget(15, 10, "manual." + entity.manualName() + ".title"), new PictureWidget(73 - (entity.pictureWidth()/2), 15, entity.manualPictureLocation(), entity.pictureWidth(), entity.pictureHeight(), entity.manualStats())).setParent().setEntity(entity));
        newPages.addAll(TextPages(15, 10, "manual." + entity.manualName() + ".description", title, isHidden));
        return newPages;
    }

    public static ArrayList<Page> pageCreatorMachines(String entryName, ItemStack machine, String imageLocation, int width, int height, boolean isHidden) {
        ArrayList<Page> newPages = new ArrayList<>();
        newPages.add(new Page(entryName, isHidden, new TextWidget(15, 10, "manual." + entryName + ".title", 1.5f), new ItemWidget(73 - 24, 77, machine, 3)).setItem(machine).setParent());
        newPages.add(new Page(entryName, isHidden, new PictureWidget(15, 10, imageLocation, width, height)));
        newPages.addAll(TextPages(16, 10, "manual." + entryName + ".description", entryName, isHidden));
        return newPages;
    }


    public static ArrayList<Page> pageCreatorItems(IManualEntryItem item, boolean isHidden) {
        ArrayList<Page> newPages = new ArrayList<>();
        String title = item.manualName(0);
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        for (int i = 0; i <= item.metas(); i++)
            items.add(new ItemStack(item.getItem(), 1, i));
        newPages.add(new Page(title, isHidden, new TextWidget(15, 10, item.manualName(0), 1.5f), new ItemWidget(49, 77, item, 3)).addItems(items).setParent());
        newPages.addAll(TextPages(16, 10, "manual." + item.manualName(0) + ".description", title, isHidden));
        ArrayList<IManualEntryItem> manualItem = new ArrayList<>();
        manualItem.add(item);
        newPages.addAll(RecipePages(manualItem, title, isHidden));
        return newPages;
    }

    public static ArrayList<Page> pageCreatorItems(String name, ArrayList<IManualEntryItem> manualItems, boolean isHidden) {
        ArrayList<Page> newPages = new ArrayList<>();
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        for (IManualEntryItem item : manualItems) {
            for (int i = 0; i <= item.metas(); i++)
                items.add(new ItemStack(item.getItem(), 1, i));
        }
        newPages.add(new Page(name, new TextWidget(15, 10, name, 1.5f), new ItemWidget(49, 77, items, 3)).addItems(items).setParent());
        newPages.addAll(TextPages(16, 10, "manual." + name + ".description", name, isHidden));
        newPages.addAll(RecipePages(manualItems, name, isHidden));
        return newPages;
    }


    public static ArrayList<Page> TextPages(int x, int y, String unlocalizedName, String pageName, boolean isHidden) {
        ArrayList<Page> newPages = new ArrayList<>();
        String text = StatCollector.translateToLocal(unlocalizedName);
        TextContainer textContainer = new TextContainer(116, 144, text);

        textContainer.setCurrentScale(1.0f).setCurrentColor(0x808080).setCurrentFormat("");
        textContainer.registerFormat(new TextFormatComponents.TextFormatNewLine());
        textContainer.registerFormat(new TextFormatComponents.TextFormatScale(1.0F));
        textContainer.registerFormat(new TextFormatComponents.TextFormatColor(0x808080));
        textContainer.registerFormat(new TextFormatComponents.TextFormatTooltip("N/A"));
        textContainer.registerFormat(new TextFormatComponents.TextFormatSimple("bold", EnumChatFormatting.BOLD));
        textContainer.registerFormat(new TextFormatComponents.TextFormatSimple("obfuscated", EnumChatFormatting.OBFUSCATED));
        textContainer.registerFormat(new TextFormatComponents.TextFormatSimple("italic", EnumChatFormatting.ITALIC));
        textContainer.registerFormat(new TextFormatComponents.TextFormatSimple("strikethrough", EnumChatFormatting.STRIKETHROUGH));
        textContainer.registerFormat(new TextFormatComponents.TextFormatSimple("underline", EnumChatFormatting.UNDERLINE));
        textContainer.registerFormat(new TextFormatComponents.TextFormatPagelink());
        textContainer.registerFormat(new TextFormatComponents.TextFormatRainbow());

        try {
            textContainer.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < textContainer.getPages().size(); i++) {
            newPages.add(new Page(pageName, isHidden, new TextWidget(x, y, unlocalizedName, i)));
        }
        return newPages;
    }

    public static ArrayList<Page> RecipePages(ArrayList<IManualEntryItem> items, String title, boolean isHidden) {
        ArrayList<Page> newPages = new ArrayList<>();
        int height = 10;
        int type = 0;
        ArrayList<ManualWidgetsBase> widgets = new ArrayList<>();
        while (type <= 6) {
            ArrayList<ItemStack> itemStacks = new ArrayList<>();
            for (IManualEntryItem item : items) {
                for (int i : item.recipeType(0)) {
                    for (int j = 0; j <= item.metas(); j++)
                        if (i == type)
                            itemStacks.add(new ItemStack(item.getItem(), 1, j));
                }
            }

            if (itemStacks.size() > 0) {
                int latestAdded = 0;
                switch (type) {
                    case 1:
                        widgets.add(new SmeltingRecipeWidget(itemStacks, 15, height));
                        height += smeltingRecipeHeight;
                        latestAdded += smeltingRecipeHeight;
                        break;
                    case 2:
                        widgets.add(new CraftingRecipeWidget(itemStacks, 15, height));
                        height += craftingRecipeHeight;
                        latestAdded += craftingRecipeHeight;
                        break;
                    case 3:
                        widgets.add(new PestleAndMortarRecipeWidget(itemStacks, 15, height));
                        height += pestleAndMortarRecipeHeight;
                        latestAdded += pestleAndMortarRecipeHeight;
                        break;
                    case 4:
                        widgets.add(new CompostRecipeWidget(15, height));
                        height += compostRecipeHeight;
                        latestAdded += compostRecipeHeight;
                        break;
                    case 5:
                        widgets.add(new PurifierRecipeWidget(itemStacks, 15, height));
                        height += purifierRecipeHeight;
                        latestAdded += purifierRecipeHeight;
                        break;
                    case 6:
                        widgets.add(new RubberTabWidget(15, height));
                        height += rubberTabRecipeHeight;
                        latestAdded += rubberTabRecipeHeight;
                        break;
                }
                if (height >= 152) {
                    ManualWidgetsBase temp = widgets.get(widgets.size() - 1);
                    widgets.remove(widgets.size() - 1);
                    Page page = new Page(title, (ArrayList<ManualWidgetsBase>) widgets.clone(), isHidden);
                    newPages.add(page);
                    widgets.clear();
                    temp.changeYStart(10);
                    widgets.add(temp);
                    height = 10 + latestAdded;
                }
            }
            type++;
        }
        if (widgets.size() > 0) {
            Page page = new Page(title, (ArrayList<ManualWidgetsBase>) widgets.clone(), isHidden);
            newPages.add(page);
            widgets.clear();
        }

        return newPages;
    }


}