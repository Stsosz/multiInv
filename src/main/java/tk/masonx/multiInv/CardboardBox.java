package tk.masonx.multiInv;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class CardboardBox implements Serializable {

	private static final long serialVersionUID = 9218747208794761041L;
	private int materialId;
	private byte data;
	private short durability;
	private int amount;
	private Map<String, Object> meta;

	@SuppressWarnings("deprecation")
	public CardboardBox(ItemStack i) {
		try {
			this.materialId = i.getTypeId();
			this.data	= i.getData().getData();
			this.durability = i.getDurability();
			this.amount = i.getAmount();

			if (i.getItemMeta() instanceof ConfigurationSerializable)
				this.meta = this.getNewMap(((ConfigurationSerializable) i.getItemMeta()).serialize());
		} catch (NullPointerException e) {
			this.materialId = 0;
			this.data=0;
			this.durability=0;
			this.amount=0;
		}
	}

	@SuppressWarnings("deprecation")
	public ItemStack getItemStack() {
		ItemStack i = new ItemStack(this.materialId);
		i.setAmount(this.amount);
		i.setDurability(this.durability);
		i.setData(new MaterialData(this.data));
		i.setDurability(this.durability);

		if (this.meta != null && !this.meta.isEmpty()) {
			i.setItemMeta((ItemMeta) ConfigurationSerialization.deserializeObject(this.meta, ConfigurationSerialization.getClassByAlias("ItemMeta")));
		}
		return i;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getNewMap(Map<String, Object> map) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		if (!map.isEmpty()) {
			for (String x : map.keySet()) {
				Object value = map.get(x);
				if (value instanceof Map) {
					value = getNewMap((Map<String, Object>) value);
				}
				newMap.put(new String(x), value);
			}
		}
		return newMap;
	}
}
