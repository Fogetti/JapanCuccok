package com.japancuccok.main.dashboard;

import com.japancuccok.common.domain.image.BaseImage;
import com.japancuccok.common.domain.image.IImage;
import com.japancuccok.common.domain.image.ImageModel;
import com.japancuccok.common.domain.product.Product;
import com.japancuccok.db.DAOService;
import com.japancuccok.db.GenericGaeDAO;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.logging.Logger;

import static com.japancuccok.common.infrastructure.tools.ImageResourceUtil.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nagy Gergely
 * Date: 2012.06.24.
 * Time: 1:19
 */
public class SliderListView<T extends BaseImage> extends ListView<T> {

    private static final long serialVersionUID = 5235676500079567095L;

    transient private static final Logger logger = Logger.getLogger(SliderListView.class.getName());

    public SliderListView(String id) {
        super(id);
    }

    public SliderListView(String id, IModel<? extends List<T>> iModel) {
        super(id, iModel);
    }

    @Override
    protected void populateItem(ListItem<T> listItem) {
        setOutputMarkupId(true);

        IImage image = listItem.getModelObject();
        listItem.add(new AttributeModifier("class", "ts-li"));
        listItem.setMarkupId("slider" + listItem.getIndex());
        listItem.add(getImageLink(image));
    }

    private Component getImageLink(IImage image) {
        PageParameters parameters = new PageParameters();
//        Product product = getProduct(image);
//        parameters.add("id", product.getId());
//        BookmarkablePageLink imageLink = new BookmarkablePageLink<BaseImage>("imageAnchor", ProductDetail.class, parameters);
//        BookmarkablePageLink imageLink = new BookmarkablePageLink<BaseImage>("imageAnchor", getPage().getClass());
        WebMarkupContainer imageLink = new WebMarkupContainer("imageAnchor");
        imageLink.setOutputMarkupId(true);
        imageLink.add(getImageWrapper(image));
        imageLink.add(getLabel(image));
        return imageLink;
    }

    private Product getProduct(IImage image) {
        GenericGaeDAO<Product> productDAO = DAOService.productDao;
        return productDAO.find(image.getKey().getKey());
    }

    private WebMarkupContainer getImageWrapper(IImage image) {
        WebMarkupContainer imageWrapper = new WebMarkupContainer("imageWrapper");
        String imageUrl = getUrl(image, this);
        imageWrapper.add(getPlaceHolder());
        String bgImage = "background-image:url('"+imageUrl+"');";
        String bgRepeat = "background-repeat:no-repeat;";
        String alphaImageLoader1 = "filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='"+imageUrl+"', sizingMethod='scale');";
        String alphaImageLoader2 = "-ms-filter: \"progid:DXImageTransform.Microsoft.AlphaImageLoader( src='"+imageUrl+"', sizingMethod='scale')\";";
        imageWrapper.add(new AttributeModifier("style",
                new Model<String>(bgImage+bgRepeat+alphaImageLoader1+alphaImageLoader2)));
        return imageWrapper;
    }

    private Component getPlaceHolder() {
        String componentId = "placeHolder";
        WebMarkupContainer placeHolder = new WebMarkupContainer(componentId);
        return placeHolder;
    }

    private Label getLabel(IImage image) {
        Label label = new Label("imageLabel", new DescriptionPropertyModel(new ImageModel(image.getId()), "description"));
        label.setEscapeModelStrings(false);
        return label;
    }

}
