package me.nickimpact.gts.ui;

import com.google.common.collect.Lists;
import com.nickimpact.impactor.sponge.ui.SpongeIcon;
import com.nickimpact.impactor.sponge.ui.SpongeLayout;
import com.nickimpact.impactor.sponge.ui.SpongeUI;
import me.nickimpact.gts.common.config.MsgConfigKeys;
import me.nickimpact.gts.common.config.wrappers.TitleLorePair;
import me.nickimpact.gts.sponge.text.SpongeMessageService;
import me.nickimpact.gts.util.GTSReferences;
import me.nickimpact.gts.util.items.SkullCreator;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

import static me.nickimpact.gts.util.GTSReferences.readMessageConfigOption;

public class SpongeMainMenu {

	private static final SpongeMessageService PARSER = GTSReferences.PARSER;

	private final SpongeUI view;
	private final Player viewer;

	public SpongeMainMenu(Player viewer) {
		this.viewer = viewer;
		this.view = this.construct(viewer);
	}

	public void open() {
		this.view.open(this.viewer);
	}

	private SpongeUI construct(Player viewer) {
		return SpongeUI.builder()
				.title(PARSER.getForSource(readMessageConfigOption(MsgConfigKeys.UI_MAIN_TITLE), viewer, null, null))
				.dimension(InventoryDimension.of(9, 5))
				.build()
				.define(this.display());
	}

	private SpongeLayout display() {
		SpongeLayout.SpongeLayoutBuilder slb = SpongeLayout.builder();
		slb.dimension(9, 5);
		slb.border();

		TitleLorePair browse = readMessageConfigOption(MsgConfigKeys.UI_MAIN_BROWSER);
		SpongeIcon browser = new SpongeIcon(ItemStack.builder()
				.from(SkullCreator.fromBase64("MmUyY2M0MjAxNWU2Njc4ZjhmZDQ5Y2NjMDFmYmY3ODdmMWJhMmMzMmJjZjU1OWEwMTUzMzJmYzVkYjUwIn19fQ=="))
				.add(Keys.DISPLAY_NAME, PARSER.getForSource(browse.getTitle(), this.viewer, null, null))
				.add(Keys.ITEM_LORE, PARSER.getTextListForSource(browse.getLore(), this.viewer, null, null))
				.build()
		);
		browser.addListener(clickable -> {
			SpongeListingMenu b = new SpongeListingMenu(this.viewer);
			b.open();
		});
		slb.slot(browser, 22);

		TitleLorePair selling = readMessageConfigOption(MsgConfigKeys.UI_MAIN_SELL);
		SpongeIcon sell = new SpongeIcon(ItemStack.builder()
				.from(SkullCreator.fromBase64("N2UzZGViNTdlYWEyZjRkNDAzYWQ1NzI4M2NlOGI0MTgwNWVlNWI2ZGU5MTJlZTJiNGVhNzM2YTlkMWY0NjVhNyJ9fX0="))
				.add(Keys.DISPLAY_NAME, PARSER.getForSource(selling.getTitle(), this.viewer, null, null))
				.add(Keys.ITEM_LORE, PARSER.getTextListForSource(selling.getLore(), this.viewer, null, null))
				.build()
		);
		sell.addListener(clickable -> {

		});
		slb.slot(sell, 11);

		this.createStashIcon(slb);

		TitleLorePair auctions = readMessageConfigOption(MsgConfigKeys.UI_MAIN_CREATE_AUCTION);
		SpongeIcon auction = new SpongeIcon(ItemStack.builder()
				.itemType(ItemTypes.GOLD_BLOCK)
				.add(Keys.DISPLAY_NAME, PARSER.getForSource(auctions.getTitle(), this.viewer, null, null))
				.add(Keys.ITEM_LORE, PARSER.getTextListForSource(auctions.getLore(), this.viewer, null, null))
				.build()
		);
		auction.addListener(clickable -> {

		});
		slb.slot(auction, 29);

		TitleLorePair cBids = readMessageConfigOption(MsgConfigKeys.UI_MAIN_CURRENT_BIDS);
		SpongeIcon bids = new SpongeIcon(ItemStack.builder()
				.itemType(ItemTypes.KNOWLEDGE_BOOK)
				.add(Keys.DISPLAY_NAME, PARSER.getForSource(cBids.getTitle(), this.viewer, null, null))
				.add(Keys.ITEM_LORE, PARSER.getTextListForSource(cBids.getLore(), this.viewer, null, null))
				.build()
		);
		bids.addListener(clickable -> {

		});
		slb.slot(bids, 33);

		SpongeIcon trademark = new SpongeIcon(ItemStack.builder()
				.from(SkullCreator.fromBase64("ZTdhNWU1MjE4M2U0MWIyOGRlNDFkOTAzODg4M2QzOTlkYzU4N2Q0ZWIyMzBlNjk2ZDhmNmJlNmQzZTU3Y2YifX19"))
				.add(Keys.DISPLAY_NAME, Text.of(TextColors.GOLD, "Bidoof"))
				.add(Keys.ITEM_LORE, Lists.newArrayList(
						Text.of(TextColors.GRAY, "What a handsome fella")
				))
				.build());
		slb.slot(trademark, 44);

		return slb.build();
	}

	private void createStashIcon(SpongeLayout.SpongeLayoutBuilder slb) {
		TitleLorePair stashRef = readMessageConfigOption(MsgConfigKeys.UI_MAIN_STASH);
		ItemStack icon = ItemStack.builder()
				.itemType(ItemTypes.CHEST)
				.add(Keys.DISPLAY_NAME, PARSER.getForSource(stashRef.getTitle(), this.viewer, null, null))
				.build();

		SpongeIcon stash = new SpongeIcon(icon);
		this.writeStashIconLore(stash, stashRef.getLore());
		stash.addListener(clickable -> {

		});
		slb.slot(stash, 15);
	}

	private void writeStashIconLore(SpongeIcon icon, List<String> loreBase) {
		List<String> lore = Lists.newArrayList();
		lore.addAll(loreBase);
		lore.add("");
		lore.add(readMessageConfigOption(MsgConfigKeys.UI_MAIN_STASH_CLICK_NOTIF));

//		GTSPlugin.getInstance().getStorage().fetchStash(this.viewer.getUniqueId()).thenAccept(
//				stash -> {
//					if(!stash.isEmpty()) {
//						Map<String, PlaceholderParser> tokens = Maps.newHashMap();
//						tokens.put("gts_stash_size", placeholder -> LegacyComponentSerializer.legacy().deserialize("" + stash.getSize()));
//
//						GTSPlugin.getInstance().getScheduler().executeSync(() -> {
//							lore.clear();
//							lore.addAll(loreBase);
//							lore.add("");
//							lore.addAll(readMessageConfigOption(MsgConfigKeys.UI_MAIN_STASH_ITEMS_AVAILABLE));
//							lore.add("");
//							lore.add(readMessageConfigOption(MsgConfigKeys.UI_MAIN_STASH_CLICK_NOTIF));
//
//							icon.getDisplay().offer(Keys.ITEM_LORE, PARSER.getTextListForSource(lore, this.viewer, tokens, null));
//							this.view.setSlot(15, icon);
//						});
//					}
//				}
//		);

		icon.getDisplay().offer(Keys.ITEM_LORE, PARSER.getTextListForSource(lore, this.viewer, null, null));
	}
}