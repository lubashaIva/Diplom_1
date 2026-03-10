

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    private Burger burger;

    @Mock
    private Bun bun;
    @Mock
    private Ingredient ingredient, ingredient2, ingredient3;

    @Before
    public void createBurger() {
        burger = new Burger();
        // Гарантируем, что getName() не вернёт null
        Mockito.when(bun.getName()).thenReturn("bun");
        Mockito.when(ingredient.getName()).thenReturn("ingredient1");
        Mockito.when(ingredient2.getName()).thenReturn("ingredient2");
        Mockito.when(ingredient3.getName()).thenReturn("ingredient3");
    }

    @Test
    public void setBunTest() {
        burger.setBuns(bun);
        assertEquals("bun", burger.bun.getName());
    }

    @Test
    public void addIngredientTest() {
        burger.addIngredient(ingredient);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void removeIngredientTest() {
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredient2);
        burger.addIngredient(ingredient3);
        assertEquals(3, burger.ingredients.size());

        burger.removeIngredient(2);
        assertEquals(2, burger.ingredients.size());
    }

    @Test
    public void moveIngredientTest() {
        burger.addIngredient(ingredient);  // индекс 0
        burger.addIngredient(ingredient2); // индекс 1
        burger.addIngredient(ingredient3); // индекс 2

        burger.moveIngredient(1, 2); // перемещаем ingredient2 (индекс 1) на позицию 2

        // После перемещения:
        // индекс 0: ingredient
        // индекс 1: ingredient3
        // индекс 2: ingredient2
        assertEquals(ingredient, burger.ingredients.get(0));
        assertEquals(ingredient3, burger.ingredients.get(1));
        assertEquals(ingredient2, burger.ingredients.get(2));
    }

    @Test
    public void getPriceTest() {
        burger.setBuns(bun);
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredient2);

        Mockito.when(bun.getPrice()).thenReturn(20F);
        Mockito.when(ingredient.getPrice()).thenReturn(10F);
        Mockito.when(ingredient2.getPrice()).thenReturn(2F);

        float expected = (20F * 2) + 10F + 2F; // 52F

        assertEquals(expected, burger.getPrice(), 0.01F); // дельта 0.01F для float
    }

    @Test
    public void getReceiptTest() {
        burger.setBuns(bun);
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredient2);
        burger.addIngredient(ingredient3);

        Mockito.when(bun.getName()).thenReturn("burger");
        Mockito.when(ingredient.getName()).thenReturn("ingredient1");
        Mockito.when(ingredient2.getName()).thenReturn("ingredient2");
        Mockito.when(ingredient3.getName()).thenReturn("ingredient3");
        Mockito.when(bun.getPrice()).thenReturn(20F);
        Mockito.when(ingredient.getPrice()).thenReturn(10F);
        Mockito.when(ingredient2.getPrice()).thenReturn(2F);
        Mockito.when(ingredient3.getPrice()).thenReturn(3F);
        Mockito.when(ingredient.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(ingredient2.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(ingredient3.getType()).thenReturn(IngredientType.FILLING);

        String expected = String.format("(==== burger ====)%n" +
                "= sauce ingredient1 =%n" +
                "= filling ingredient2 =%n" +
                "= filling ingredient3 =%n" +
                "(==== burger ====)%n" +
                "%nPrice: 55,000000%n");

        assertEquals(expected, burger.getReceipt());
    }
}
