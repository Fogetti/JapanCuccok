package com.japancuccok.main.stuff;

import com.japancuccok.common.domain.category.CategoryType;
import com.japancuccok.common.pattern.GeneralPageImageLoadStrategy;
import com.japancuccok.common.wicket.component.ProductListView;
import com.japancuccok.common.wicket.template.ShopBasePage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Created with IntelliJ IDEA.
 * User: Nagy Gergely
 * Date: 2012.07.08.
 * Time: 15:34
 */
public class Stuff extends ShopBasePage {

    private static final long serialVersionUID = 5067030797247221174L;

    public Stuff() {
        ProductListView productListView = new ProductListView("productRow", new GeneralPageImageLoadStrategy(CategoryType.STUFF));
        add(productListView);
    }

    @Override
    public Label getHeaderTitle() {
        return new Label("headerTitle", "Nézd meg cuccaink választékát!");
    }

}
