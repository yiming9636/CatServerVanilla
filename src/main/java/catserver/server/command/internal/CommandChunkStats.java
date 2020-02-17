package catserver.server.command.internal;

import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.FMLLog;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandChunkStats extends Command {
    private static Map<Chunk, Long> chunks = new HashMap<>();
    public static boolean chunkStats = false;

    public CommandChunkStats(String name) {
        super(name);
        this.description = "Chunk Stats Command";
        this.usageMessage = "/chunkstats start/stop";
        setPermission("catserver.command.chunkstats");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) return true;
        if (args.length != 1) return false;
        if (args[0].equals("start")) {
            chunks = new HashMap<>();
            chunkStats = true;
            sender.sendMessage("Chunk stats started.");
            return true;
        }else if (args[0].equals("stop")) {
            sender.sendMessage("Checking... Please see console");
            List<ChunkTime> chunkList = new ArrayList<>();
            for (int i = 0; i < 5 ; i++) {
                Chunk hight = null;
                long t = 0;
                for (Chunk chunk : chunks.keySet()) {
                    if (hight == null) {
                        hight = chunk;
                        t = chunks.get(chunk);
                        continue;
                    }
                    long tt = chunks.get(chunk);
                    if (tt > t) {
                        hight = chunk;
                        t = tt;
                    }
                }
                chunkList.add(new ChunkTime(hight, t));
                chunks.remove(hight);
            }
            FMLLog.log.info("Chunks Time:");
            for (ChunkTime chunkTime : chunkList) {
                Chunk chunk = chunkTime.chunk;
                FMLLog.log.info("World:{} X:{} Z:{}, has run time: {} ns", chunk.getWorld().worldInfo.getWorldName(), chunk.x << 4, chunk.z << 4, chunkTime.time);
            }
            return true;
        }else {
            return false;
        }
    }

    public static void addTime(Chunk chunk, long time) {
        if (! chunkStats) return;
        Long oldTime = chunks.get(chunk);
        if (oldTime == null)
            oldTime = 0L;
        oldTime += time;
        chunks.put(chunk, oldTime);
    }

    class ChunkTime {
        public final Chunk chunk;
        public final long time;

        public ChunkTime(Chunk chunk, long time) {
            this.chunk = chunk;
            this.time = time;
        }
    }
}
