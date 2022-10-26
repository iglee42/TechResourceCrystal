package fr.iglee42.techresourcecrystal.customize.custompack;

import fr.iglee42.techresourcecrystal.customize.DataGeneratorFactory;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;

import java.nio.file.Path;
import java.util.function.Consumer;

public class TRCPackFinder implements RepositorySource {


	private final PackType type;

	public TRCPackFinder(PackType type) {

		this.type = type;
	}


	@Override
	public void loadPacks(Consumer<Pack> infoConsumer, Pack.PackConstructor infoFactory) {
		Path rootPath = DataGeneratorFactory.ROOT_PATH;

		Pack pack = Pack.create("trc_" + type.getSuffix(), true,
				() -> new InMemoryPack(rootPath), infoFactory, Pack.Position.BOTTOM, Component::plainCopy);
		if (pack != null) {
			infoConsumer.accept(pack);
		}
	}
}