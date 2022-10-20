## GoodShops

Ever wanted to allow your server players to have their own shop? Well, with GoodShops, they actually can; and that's not all, administrators can also create a global market.

### Features

* Global Market/Shop
  * A global market that only administrators can manage/edit. Everything in this market is editable.
  * Features
    * Market Sections
    * In-game Inventory Editor
    * Discounts based on Permissions
    * Sell Items Section
* Per-Player Shop
  * Allows players to create their own shop by right clicking a chest while sneaking and holding the desired item they want to create in-hand.
  * Features
    * Adjust item price
    * Adjust price discount
    * Auto adjust price based on purchase-rate and base price. (This can be turned off by shop owner or globally in configuration)
    * Change/set transaction success message. (This can be configured by shop owner or globally in configuration)
* Commands
  * /sell
    * Opens an inventory where you can sell items.
  * /market
    * Does the same as /shop
  * /shop
    * Opens the global shop/market
  * /goodshops {sub command}
    * Executes a sub-command from GoodShops original command.

### Integrations

* Custom NBT data
  * GoodItems
* Economy
  * Vault
  * GoodCurrencies
