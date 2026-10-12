# CasperBoost 1.12.2 - Ultra FPS Optimizer

Engineered specifically for low-end hardware, budget laptops, and the **Casper Nirvana C370** (Intel Celeron N4020 @ 1.10GHz, Intel UHD Graphics 600, 4GB RAM).

## Key Features
1. **Math Optimizations (`FastMath`)**:
   - Replaces CPU-expensive trigonometric calls with a static precomputed lookup table and bitmask wrapping.
   - Zero memory allocations, friendly to dual-core Celeron caching.
2. **Dynamic Entity & TileEntity Culling (`RenderCullingHandler`)**:
   - **FOV / Frustum Culling**: Skips rendering mobs that are behind the player's view angle, eliminating hundreds of wasted draw calls.
   - **Distance Culling**: Caps mob rendering at tight thresholds (28 blocks for hostile, 20 blocks for passive animals, 16 blocks for items/XP).
3. **Fog Bypass & Nullifier**:
   - Eliminates OpenGL fog density passes to maximize fillrate on integrated Intel UHD 600 GPUs.
4. **Casper C370 Memory Watcher (`MemoryOptimizer`)**:
   - Prevents memory leaks and mitigates stutter spikes from Windows pagefile thrashing on 4GB RAM systems.
5. **Fully Configurable**:
   - Generates `config/casperboost.cfg` where every optimization can be adjusted.

## How to Build
### Method 1: GitHub Actions (Automatic, Recommended)
1. Push this folder to a GitHub repository or fork.
2. The included `.github/workflows/build.yml` will automatically build the `.jar` using Gradle 4.9 and ForgeGradle.
3. Download the built `CasperBoost-1.12.2-1.0.0.jar` from the Actions artifact tab and drop it in your `.minecraft/mods` folder.

### Method 2: Local Gradle
Run:
```bash
gradle setupCIWorkspace build
```
The output jar will be in `build/libs/CasperBoost-1.12.2-1.0.0.jar`.
