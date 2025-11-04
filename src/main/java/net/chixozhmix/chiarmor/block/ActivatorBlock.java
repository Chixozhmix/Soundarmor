package net.chixozhmix.chiarmor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ActivatorBlock extends Block {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final int DETECTION_RADIUS = 5;
    private static final double ACTIVATION_CHANCE = 0.15;

    public ActivatorBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, net.minecraft.world.level.BlockGetter level, BlockPos pos, net.minecraft.core.Direction direction) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getLevel().isClientSide()) return;

        Level level = (Level) event.getLevel();
        BlockPos brokenPos = event.getPos();

        // Ищем детекторы в радиусе 10 блоков
        for (int x = -DETECTION_RADIUS; x <= DETECTION_RADIUS; x++) {
            for (int y = -DETECTION_RADIUS; y <= DETECTION_RADIUS; y++) {
                for (int z = -DETECTION_RADIUS; z <= DETECTION_RADIUS; z++) {
                    BlockPos checkPos = brokenPos.offset(x, y, z);

                    if (level.getBlockState(checkPos).getBlock() instanceof ActivatorBlock) {
                        // Проверяем расстояние
                        if (brokenPos.distSqr(checkPos) <= DETECTION_RADIUS * DETECTION_RADIUS) {
                            // 10% шанс активации
                            if (level.random.nextDouble() < ACTIVATION_CHANCE) {
                                activateDetector(level, checkPos);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void activateDetector(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        if (!state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(POWERED, true), 3);
            level.scheduleTick(pos, state.getBlock(), 20); // Выключаем через 1 секунду (20 тиков)
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(POWERED, false), 3);
        }
    }
}
