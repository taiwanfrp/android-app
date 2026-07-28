# Fix build error: Invalid resource file extension

The project is failing to build because `ic_discord_custom.svg` is located in the `res/drawable` directory. Android resources in `drawable` must have `.xml` or `.png` (or other supported) extensions. SVG files must be converted to XML VectorDrawables.

## Proposed Changes

### [Component Name]

#### [NEW] [ic_discord_custom.xml](file:///C:/Users/ojhgg/AndroidStudioProjects/taiwamfrp/app/src/main/res/drawable/ic_discord_custom.xml)
Create a VectorDrawable equivalent of the existing SVG file.

#### [DELETE] [ic_discord_custom.svg](file:///C:/Users/ojhgg/AndroidStudioProjects/taiwamfrp/app/src/main/res/drawable/ic_discord_custom.svg)
Remove the unsupported SVG file.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:packageDebugResources` to ensure the resource packaging task now succeeds.
- Run a full build: `./gradlew assembleDebug`.

### Manual Verification
- Check if the icon renders correctly in the app if possible (e.g., via Compose Preview if a preview exists for the usage).
