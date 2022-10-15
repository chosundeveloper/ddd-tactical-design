package kitchenpos.menus.tobe.application;


import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.dto.menu.ChangePriceRequest;
import kitchenpos.products.tobe.domain.Price;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final ChangePriceRequest request) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.changePrice(new Price(request.price()));
        return menu;
    }
}
