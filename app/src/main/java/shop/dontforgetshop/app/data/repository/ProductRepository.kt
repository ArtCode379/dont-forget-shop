package shop.dontforgetshop.app.data.repository

import shop.dontforgetshop.app.R
import shop.dontforgetshop.app.data.model.Product
import shop.dontforgetshop.app.data.model.ProductCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductRepository {
    private val products: List<Product> = listOf(
        Product(
            id = 1,
            title = "Smart Air Purifier",
            description = "Advanced HEPA H13 filtration system that removes 99.97% of airborne particles. Covers up to 50m², features real-time air quality monitoring, whisper-quiet operation at 25dB, and auto sleep mode. WiFi-enabled with companion app control.",
            category = ProductCategory.SMART_HOME,
            price = 149.99,
            imageRes = R.drawable.img_air_purifier,
        ),
        Product(
            id = 2,
            title = "Espresso Coffee Machine",
            description = "Professional barista-grade machine with 15-bar pump pressure and dual thermoblock system. Brews authentic espresso, cappuccino, and latte with consistent precision. Includes integrated burr grinder, 2L removable water tank, and steam wand.",
            category = ProductCategory.KITCHEN_APPLIANCES,
            price = 299.99,
            imageRes = R.drawable.img_coffee_machine,
        ),
        Product(
            id = 3,
            title = "Robot Vacuum Cleaner",
            description = "AI-powered navigation system with LiDAR mapping creates precise floor plans for methodical cleaning. 3000Pa suction power tackles carpet and hard floors. Obstacle detection, multi-zone scheduling, and automatic charging make daily cleaning effortless.",
            category = ProductCategory.CLEANING,
            price = 249.99,
            imageRes = R.drawable.img_robot_vacuum,
        ),
        Product(
            id = 4,
            title = "Ceramic Table Lamp",
            description = "Handcrafted minimalist ceramic base in matte finish with a warm-tone linen shade. The organic form and artisanal texture make each piece unique. Compatible with E27 bulbs up to 60W. Perfect for bedside tables, reading nooks, and console tables.",
            category = ProductCategory.LIGHTING,
            price = 79.99,
            imageRes = R.drawable.img_table_lamp,
        ),
        Product(
            id = 5,
            title = "Smart Thermostat",
            description = "WiFi-enabled climate control that learns your schedule and adjusts automatically for comfort and efficiency. Compatible with most boiler and HVAC systems. Large colour touchscreen, voice assistant compatible, and reports monthly energy savings.",
            category = ProductCategory.SMART_HOME,
            price = 129.99,
            imageRes = R.drawable.img_thermostat,
        ),
        Product(
            id = 6,
            title = "Cast Iron Dutch Oven",
            description = "Premium 5.5L enamelled cast iron Dutch oven crafted for superior heat retention and even distribution. The tight-fitting lid seals in moisture for tender braises and slow cooks. Oven-safe to 260°C, dishwasher safe, and comes in a timeless matte finish.",
            category = ProductCategory.KITCHEN_APPLIANCES,
            price = 89.99,
            imageRes = R.drawable.img_dutch_oven,
        ),
        Product(
            id = 7,
            title = "Rainfall Shower Head",
            description = "Luxury 300mm stainless steel rainfall shower head with 120 anti-clog nozzles. Multiple spray modes including rainfall, massage, and mist. The adjustable arm provides flexible positioning. The anti-fingerprint finish resists water spots for a lasting shine.",
            category = ProductCategory.BATHROOM,
            price = 69.99,
            imageRes = R.drawable.img_shower_head,
        ),
        Product(
            id = 8,
            title = "Velvet Throw Pillow Set",
            description = "Set of 4 decorative cushions crafted from premium soft velvet fabric. Available in a curated palette of warm neutrals and jewel tones. Hidden zip closure with plush hollow fibre inserts included. 45×45cm. Machine washable covers.",
            category = ProductCategory.HOME_DECOR,
            price = 49.99,
            imageRes = R.drawable.img_pillows,
        ),
        Product(
            id = 9,
            title = "Electric Stand Mixer",
            description = "800W professional-grade stand mixer with 5L stainless steel bowl and 10 speed settings. Planetary mixing action reaches every part of the bowl. Includes dough hook, flat beater, and whisk. Tilt-head design and 3 attachment hubs for versatile food preparation.",
            category = ProductCategory.KITCHEN_APPLIANCES,
            price = 199.99,
            imageRes = R.drawable.img_stand_mixer,
        ),
        Product(
            id = 10,
            title = "LED Strip Lights Kit",
            description = "5m smart RGB LED strip with 16 million colours and dynamic scene modes. WiFi-enabled for app or voice control. Self-adhesive backing for easy installation under cabinets, behind TVs, or along ceilings. Includes controller, remote, and power adapter.",
            category = ProductCategory.LIGHTING,
            price = 34.99,
            imageRes = R.drawable.img_led_lights,
        ),
        Product(
            id = 11,
            title = "Bamboo Bath Caddy",
            description = "Extendable solid bamboo bathtub tray that fits any standard bath. Features a built-in book/tablet stand, wine glass holder, and storage compartments. The water-resistant FSC-certified bamboo surface is both elegant and easy to clean.",
            category = ProductCategory.BATHROOM,
            price = 39.99,
            imageRes = R.drawable.img_bath_caddy,
        ),
        Product(
            id = 12,
            title = "Wall Art Canvas Set",
            description = "Modern abstract 3-piece canvas set printed on gallery-quality cotton canvas with UV-resistant inks. Each panel features flowing organic forms in warm earth tones with gold accents. Ready-to-hang with pre-installed wall fixtures. Total width 120cm.",
            category = ProductCategory.HOME_DECOR,
            price = 59.99,
            imageRes = R.drawable.img_wall_art,
        ),
    )

    fun observeById(id: Int): Flow<Product?> {
        val item = products.find { it.id == id }
        return flowOf(item)
    }

    fun getById(id: Int): Product? {
        return products.find { it.id == id }
    }

    fun observeAll(): Flow<List<Product>> {
        return flowOf(products)
    }
}
