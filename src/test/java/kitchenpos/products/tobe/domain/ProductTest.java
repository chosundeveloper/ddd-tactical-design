package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.NotEmptyDisplayedNameException;
import kitchenpos.products.tobe.domain.exception.NotNegativePriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("상품")
class ProductTest {

    private String displayedName = "강정 치킨";
    private BigDecimal createProductPrice = BigDecimal.valueOf(20000);

    @DisplayName("상품을 생성한다.")
    @Test
    void createProduct() {
        boolean isProfanity = false;
        assertDoesNotThrow(() -> new Product(new DisplayedName(displayedName, isProfanity), new Price(createProductPrice)));
    }

    @DisplayName("이름이 null이거나 빈칸인 상품을 생성할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createEmptyNameProduct(String name) {
        boolean isProfanity = false;
        assertThatThrownBy(() -> new Product(new DisplayedName(name, isProfanity), new Price(createProductPrice)))
                .isInstanceOf(NotEmptyDisplayedNameException.class);
    }

    @DisplayName("0원보다 적은 가격으로 상품을 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = "-1")
    void createNegativePriceProduct(BigDecimal negativePrice) {
        boolean isProfanity = false;
        assertThatThrownBy(() -> new Product(new DisplayedName(displayedName, isProfanity), new Price(negativePrice)))
                .isInstanceOf(NotNegativePriceException.class);
    }

    @DisplayName("상품의 가격을 변경한다.")
    @Test
    void changeProductPrice() {
        BigDecimal changePrice = BigDecimal.valueOf(10000);
        boolean isProfanity = false;
        Product product = new Product(new DisplayedName(displayedName, isProfanity), new Price(createProductPrice));
        product.changePrice(changePrice);
        assertThat(product.getPrice()).isEqualTo(new Price(changePrice));
    }

    @DisplayName("상품의 가격을 0원 이하로 변경할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = "-1")
    void changeNegativeProductPrice(BigDecimal negativePrice) {
        boolean isProfanity = false;
        Product product = new Product(new DisplayedName(displayedName, isProfanity), new Price(createProductPrice));
        assertThatThrownBy(() -> product.changePrice(negativePrice))
                .isInstanceOf(NotNegativePriceException.class);
    }

}
