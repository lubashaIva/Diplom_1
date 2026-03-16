import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    private Burger burger;

    @Mock
    private Bun bun;
    @Mock
    private Ingredient souceIngredient, fillingIngredient, altFillingIngredient;

    @Before
    public void createBurger() {
        burger = new Burger();
        // Гарантируем, что getName() не вернёт null
        Mockito.when(bun.getName()).thenReturn("bun");
        Mockito.when(souceIngredient.getName()).thenReturn("souceIngredient");
        Mockito.when(fillingIngredient.getName()).thenReturn("fillingIngredient");
        Mockito.when(altFillingIngredient.getName()).thenReturn("altFillingIngredient");
    }

    @Test
    public void setBunTest() {
        burger.setBuns(bun);
        assertEquals("bun", burger.bun.getName());
    }

    @Test
    public void addIngredientTest() {
        burger.addIngredient(souceIngredient);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void removeIngredientTest() {
        burger.addIngredient(souceIngredient);
        burger.addIngredient(fillingIngredient);
        burger.addIngredient(altFillingIngredient);
        burger.removeIngredient(2);
        assertEquals(2, burger.ingredients.size());
    }

    @Test
    public void moveIngredientTest() {
        burger.addIngredient(souceIngredient);  // индекс 0
        burger.addIngredient(fillingIngredient); // индекс 1
        burger.addIngredient(altFillingIngredient); // индекс 2

        burger.moveIngredient(1, 2); // перемещаем ingredient2 (индекс 1) на позицию 2

        // После перемещения:
        // индекс 0: souceIngredient
        // индекс 1: altFillingIngredient
        // индекс 2: fillingIngredient
        ArrayList<String> expectedPattern = new ArrayList<>(List.of(souceIngredient.getName(), altFillingIngredient.getName(), fillingIngredient.getName()));
        ArrayList<String> actualPattern = burger.ingredients.stream()
                .map(Ingredient::getName).collect(Collectors.toCollection(ArrayList::new));
        assertEquals(expectedPattern, actualPattern);
    }

    @Test
    public void getPriceTest() {
        burger.setBuns(bun);
        burger.addIngredient(souceIngredient);
        burger.addIngredient(fillingIngredient);

        Mockito.when(bun.getPrice()).thenReturn(20F);
        Mockito.when(souceIngredient.getPrice()).thenReturn(10F);
        Mockito.when(fillingIngredient.getPrice()).thenReturn(2F);

        float expected = (20F * 2) + 10F + 2F; // 52F

        assertEquals(expected, burger.getPrice(), 0.01F); // дельта 0.01F для float
    }
}
