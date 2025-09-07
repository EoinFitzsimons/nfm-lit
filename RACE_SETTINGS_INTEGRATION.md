# NFM-Lit Race Settings System Integration Guide

This document explains how to integrate the new Race Settings system with the existing NFM-Lit car selection interface.

## Overview

The new race settings system consists of four main components:

1. **RaceSettings.java** - Core race configuration with validation
2. **TrackMetadata.java** - Parses real stage files for track information  
3. **RaceSettingsManager.java** - Manages race configuration and game integration
4. **RaceSettingsUI.java** - UI overlay for the car selection screen

## Integration with Existing XtGraphics.carselect()

### Current Car Selection System Analysis

The existing `XtGraphics.carselect()` method (lines 4827-5150) provides:
- 3D car display with rotation
- Performance stat bars (Top Speed, Acceleration, Handling, Stunts, Strength, Endurance)
- Car unlock progression based on `sc[]` array
- Mouse/keyboard navigation
- Integration with `Phase.CARSELECT` game state

### Integration Points

#### 1. Add RaceSettingsUI to XtGraphics

```java
public class XtGraphics extends Panel implements Runnable {
    // Add race settings UI
    private RaceSettingsUI raceSettingsUI;
    
    // Initialize in constructor
    public XtGraphics() {
        // ... existing initialization
        this.raceSettingsUI = new RaceSettingsUI();
    }
}
```

#### 2. Modify carselect() Method

Add race settings rendering and interaction to the existing carselect() method:

```java
public void carselect() {
    // ... existing car selection code (lines 4827-5150)
    
    // Add race settings UI rendering after existing UI
    if (raceSettingsUI != null) {
        raceSettingsUI.render(rd, this);
    }
    
    // ... rest of existing code
}
```

#### 3. Update Mouse Handling

In the existing mouse handling code within carselect(), add race settings interaction:

```java
// In the mouse click handling section (around line 5000)
if (click != 0) {
    // Check race settings UI first
    if (raceSettingsUI != null && raceSettingsUI.handleMouseClick(mx, my)) {
        click = 0; // Consume the click
        return;
    }
    
    // ... existing mouse handling code
}
```

#### 4. Update Unlock Progression

Sync the race settings with existing unlock progression:

```java
// Add to carselect() method after sc[] array updates
if (raceSettingsUI != null) {
    RaceSettingsManager.getInstance().updateUnlockProgression(sc);
    raceSettingsUI.syncWithSettings();
}
```

## Real Game Asset Integration

### Car Models
The system uses actual car names from `CarConfig.CAR_MODELS`:
- "2000tornados", "formula7", "canyenaro", "lescrab", "nimi", etc.
- 16 total cars matching `GameFacts.numberOfCars`

### Track System  
- Parses real stage files from `data/stages/`
- NFM1 tracks: `data/stages/nfm1/1.txt` to `11.txt`
- NFM2 tracks: `data/stages/nfm2/1.txt` to `17.txt`
- Custom tracks: `data/stages/*.txt`

### Track Metadata Parsing
Each stage file contains:
```
name(Track Name)
nlaps(4)
sky(200,227,255)
ground(195,210,210)
soundtrack(music_file)
chk(checkpoint_data)
```

### Performance Stats Integration
The system respects existing car stats from `StatList.java`:
- Top Speed: `swits[]` arrays
- Acceleration: `acelf[]` arrays  
- Handling: `turn[]` and `grip[]` values
- Stunts: `airc[]` and `airs[]` values
- Strength: `moment[]` values
- Endurance: `maxmag[]` values

## Usage Example

### Basic Setup
```java
// Get race settings manager
RaceSettingsManager manager = RaceSettingsManager.getInstance();

// Update unlock progression from existing game state
manager.updateUnlockProgression(sc);

// Configure race
RaceSettings settings = manager.getCurrentSettings();
settings.setLaps(5);
settings.setAiCount(4);
settings.setDifficulty(RaceSettings.Difficulty.HARD);

// Apply settings
manager.applySettings(settings);

// Generate race configuration
RaceSettingsManager.RaceConfiguration config = manager.createRaceConfiguration();
int[] newSc = config.toScArray(); // Compatible with existing sc[] format
```

### UI Integration
```java
// In XtGraphics.carselect()
public void carselect() {
    // ... existing car selection code
    
    // Render race settings overlay
    raceSettingsUI.render(rd, this);
    
    // Handle race settings input
    if (click != 0 && raceSettingsUI.handleMouseClick(mx, my)) {
        click = 0;
    }
    
    // ... rest of existing code
}
```

## Features

### Race Configuration
- **Track Selection**: Browse unlocked tracks with metadata
- **Lap Count**: 1-10 laps with track default suggestions
- **AI Count**: 0-6 AI opponents (respects 7 total player limit)
- **Difficulty**: Easy/Normal/Hard/Nightmare with AI speed modifiers
- **Car Pool**: Select from unlocked cars

### Track Metadata
- Automatic parsing of stage files
- Default lap count from track design
- Visual theming (sky/ground colors)
- Checkpoint analysis for difficulty estimation
- Soundtrack information

### Validation
- Respects existing car unlock progression
- Validates track availability
- Ensures car/track compatibility
- Maintains game engine limits

### UI Features
- Toggle panel to show/hide race settings
- Interactive controls for all parameters
- Real-time validation feedback
- Track information display
- Integrates with existing 3D car display

## Backward Compatibility

The system is designed to be completely backward compatible:
- Existing carselect() functionality unchanged
- Race settings are optional overlay
- Uses existing `sc[]` array format
- Respects current unlock progression
- No changes to core game logic required

## File Structure
```
src/nfm/lit/
├── RaceSettings.java          - Core race configuration
├── TrackMetadata.java         - Stage file parsing
├── RaceSettingsManager.java   - Integration layer
└── RaceSettingsUI.java        - UI overlay
```

This system provides a robust, user-friendly race configuration interface while fully respecting the existing NFM-Lit game architecture and real asset data.
