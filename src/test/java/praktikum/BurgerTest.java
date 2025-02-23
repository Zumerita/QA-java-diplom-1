package praktikum;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private final Burger burger = new Burger();
    @Mock
    private Bun bun;
    @Mock
    Ingredient ingredient;
    @Mock
    Ingredient ingredientSecond;
    @Mock
    Database database;
    private final List<Bun> buns = List.of(new Bun("grey bun", 100.50F));

    private final Ingredient ingredientSauce = new Ingredient(IngredientType.SAUCE, "hot sauce", 100F);
    private final Ingredient ingredientFilling = new Ingredient(IngredientType.FILLING, "sausage", 300F);

    @Before
    public void setDefaultBun() {
        Mockito.when(database.availableBuns()).thenReturn(buns);
    }

    @Test
    public void setBunTest(){

        burger.setBuns(bun);
        assertEquals(bun.getName(),burger.bun.getName());
    }

    @Test
    public void checkSetBuns() {
        burger.setBuns(database.availableBuns().get(0));
        String bunName = "grey bun";
        assertEquals(bunName, burger.bun.getName());
    }

    @Test
    public void checkGetPrice() {
        Mockito.when(ingredient.getPrice()).thenReturn(125.5F);
        Mockito.when(ingredientSecond.getPrice()).thenReturn(250F);
        burger.setBuns(database.availableBuns().get(0));
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredientSecond);
        float expectedBurgerPrice = 576.5F;
        assertEquals("Некорректная цена бургера с двумя добавленными ингредиентами", expectedBurgerPrice, burger.getPrice(), 0);
    }

    @Test
    public void checkGetReceipt() {
        Mockito.when(ingredient.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(ingredient.getName()).thenReturn("hot sauce");
        Mockito.when(ingredient.getPrice()).thenReturn(100F);
        Mockito.when(ingredientSecond.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(ingredientSecond.getName()).thenReturn("sausage");
        Mockito.when(ingredientSecond.getPrice()).thenReturn(300F);
        burger.setBuns(database.availableBuns().get(0));
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredientSecond);
        String expected = "(==== grey bun ====)\r\n" +
                "= sauce hot sauce =\r\n" +
                "= filling sausage =\r\n" +
                "(==== grey bun ====)\r\n" +
                "\r\n" +
                "Price: 601,000000\r\n";
        assertEquals(expected, burger.getReceipt());
    }
}
