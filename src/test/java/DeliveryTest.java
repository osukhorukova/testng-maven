import olenzing.CargoDimension;
import olenzing.Delivery;
import olenzing.ServiceWorkload;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertThrows;

public class DeliveryTest {
    @DataProvider(
            name = "minimumDeliveryPriceCargo"
    )
    public Object[][] minimumDeliveryPriceCargo() {
        return new Object[][]{
                {new Delivery(1, CargoDimension.SMALL, false, ServiceWorkload.NORMAL), 400},
                {new Delivery(5, CargoDimension.SMALL, false, ServiceWorkload.NORMAL), 400},
                {new Delivery(30, CargoDimension.SMALL, false, ServiceWorkload.NORMAL), 400}
        };
    }

    @Test(
            groups = {"positive"},
            dataProvider = "minimumDeliveryPriceCargo"
    )
    public void minimumDeliveryPriceTest(Delivery delivery, double expectedCost) {
        Assert.assertEquals(delivery.calculateDeliveryCost(), expectedCost);
    }

    @DataProvider(
            name = "maximumDeliveryPriceCargo"
    )
    public Object[][] maximumDeliveryPriceCargo() {
        return new Object[][]{
                {new Delivery(30, CargoDimension.LARGE, true, ServiceWorkload.VERY_HIGH), 1120},
                {new Delivery(23, CargoDimension.LARGE, true, ServiceWorkload.VERY_HIGH), 1120},
                {new Delivery(11, CargoDimension.LARGE, true, ServiceWorkload.VERY_HIGH), 1120}
        };
    }

    @Test(
            groups = {"positive"},
            dataProvider = "maximumDeliveryPriceCargo"
    )
    public void maximumDeliveryPriceTest(Delivery delivery, double expectedCost) {
        Assert.assertEquals(delivery.calculateDeliveryCost(), expectedCost);
    }

    @DataProvider(
            name = "distanceBoundaryValues"
    )
    public Object[][] distanceBoundaryValues() {
        return new Object[][]{
                {new Delivery(2, CargoDimension.SMALL, false, ServiceWorkload.NORMAL), 400},
                {new Delivery(9, CargoDimension.LARGE, true, ServiceWorkload.HIGH), 840},
                {new Delivery(10, CargoDimension.SMALL, false, ServiceWorkload.NORMAL), 400},
                {new Delivery(11, CargoDimension.LARGE, true, ServiceWorkload.HIGH), 980},
                {new Delivery(29, CargoDimension.SMALL, true, ServiceWorkload.VERY_HIGH), 960},
                {new Delivery(30, CargoDimension.LARGE, false, ServiceWorkload.INCREASED), 480}
        };
    }

    @Test(
            groups = {"positive"},
            dataProvider = "distanceBoundaryValues"
    )
    public void distanceBoundaryValuesTest(Delivery delivery, double expectedCost) {
        Assert.assertEquals(delivery.calculateDeliveryCost(), expectedCost);
    }


    @Test(
            groups = {"positive"},
            description = "The cargo is fragile and the distance is 30 km"
    )
    public void fragileAndShortDistancePriceTest() {
        Delivery delivery = new Delivery(30, CargoDimension.SMALL, true, ServiceWorkload.INCREASED);
        double actualPrice = delivery.calculateDeliveryCost();
        Assert.assertEquals(actualPrice, 720);
    }

    @Test(
            groups = {"negative"},
            description = "Workload cannot be null",
            expectedExceptions = NullPointerException.class,
            expectedExceptionsMessageRegExp = "^Cannot invoke \\\"olenzing\\.ServiceWorkload\\.getDeliveryRate\\(\\)\\\" because \\\"this\\.deliveryServiceWorkload\\\" is null$"
    )
    public void nullPointerExceptionTest() {
        Delivery delivery = new Delivery(1, CargoDimension.SMALL, false, null);
        delivery.calculateDeliveryCost();
    }

    @Test(
            groups = {"negative"},
            description = "The cargo is fragile and the distance is more than 30 km",
            expectedExceptions = UnsupportedOperationException.class,
            expectedExceptionsMessageRegExp = "Fragile cargo cannot be delivered for the distance more than 30"
    )
    public void fragileAndLongDistancePriceTest() {
        Delivery delivery = new Delivery(31, CargoDimension.LARGE, true, ServiceWorkload.NORMAL);
        delivery.calculateDeliveryCost();
    }

    @Test(
            groups = {"negative"},
            description = "Negative delivery distance",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "destinationDistance should be a positive number!"
    )
    public void testNegativeDistanceOrderCost() {
        Delivery delivery = new Delivery(-1, CargoDimension.SMALL, false, ServiceWorkload.NORMAL);
        delivery.calculateDeliveryCost();
    }

}
