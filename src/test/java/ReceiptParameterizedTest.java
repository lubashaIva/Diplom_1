import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ReceiptParameterizedTest {
    private Burger burger;
    private final List<Ingredient> ingredients;
    private final Bun bun;

    public ReceiptParameterizedTest(Bun bun, List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.bun = bun;
    }

    @Parameterized.Parameters(name = "DataSet: {index}")
    public static Object[][] getDateSetForOrder() {
        return new Object[][]{
                {
                    new Bun("bread", 20F),
                    new ArrayList<>(List.of(
                        new Ingredient(IngredientType.SAUCE, "chili", 20F),
                        new Ingredient(IngredientType.FILLING, "beef", 30F),
                        new Ingredient(IngredientType.SAUCE, "ranch", 30F)
                    ))
                },
                {
                        new Bun("salad", 30F),
                        new ArrayList<>(List.of(
                                new Ingredient(IngredientType.SAUCE, "chili", 20F)
                        ))
                },
                {
                        new Bun("salad", 30F),
                        new ArrayList<>()
                }
        };
    }

    @Before
    public void createBurger() {
        this.burger = new Burger();
    }

    @Test
    public void getReceiptTest() {
        burger.setBuns(bun);
        ingredients.forEach(burger::addIngredient);
        StringBuilder actualResult = new StringBuilder(String.format("(==== %s ====)%n", bun.getName()));
        for (Ingredient ingredient : ingredients) {
            actualResult.append(String.format("= %s %s =%n", ingredient.getType().toString().toLowerCase(), ingredient.getName()));
        }
        actualResult.append(String.format("(==== %s ====)%n", bun.getName()));
        double totalPrice = bun.getPrice() * 2;
        for (Ingredient ingredient : ingredients) {
            totalPrice += ingredient.getPrice();
        }
        actualResult.append(String.format("%nPrice: %f%n", totalPrice));
        assertEquals(actualResult.toString(), burger.getReceipt());
    }
}
