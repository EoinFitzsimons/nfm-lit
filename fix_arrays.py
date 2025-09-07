#!/usr/bin/env python3

# Script to add missing 5 values to all EnhancedStatList arrays
# This generates the complete corrected arrays

arrays_to_fix = [
    ("ENHANCED_BOUNCE", "1.08F, 1.12F, 1.06F, 1.1F", "1.05F, 1.0F, 0.95F, 1.08F, 1.02F"),
    ("ENHANCED_SIMAG", "0.87F, 0.89F, 0.85F, 0.88F", "0.86F, 0.84F, 0.82F, 0.88F, 0.85F"),
    ("ENHANCED_MOMENT", "1.65F, 1.7F, 1.6F, 1.68F", "1.6F, 1.8F, 2.0F, 1.7F, 1.75F"),
    ("ENHANCED_COMPRAD", "0.52F, 0.55F, 0.5F, 0.53F", "0.48F, 0.45F, 0.42F, 0.50F, 0.47F"),
    ("ENHANCED_PUSH", "3, 3, 3, 3", "2, 3, 4, 2, 3"),
    ("ENHANCED_REVPUSH", "2, 2, 2, 2", "2, 2, 1, 2, 2"),
    ("ENHANCED_LIFT", "22, 25, 20, 23", "18, 20, 25, 19, 22"),
    ("ENHANCED_REVLIFT", "8, 6, 10, 7", "6, 5, 4, 7, 6"),
    ("ENHANCED_POWERLOSS", "3200000, 3400000, 3000000, 3300000", "3100000, 2900000, 2700000, 3300000, 3000000"),
    ("ENHANCED_FLIPY", "-68, -72, -65, -70", "-66, -64, -62, -69, -67"),
    ("ENHANCED_MSQUASH", "6, 7, 5, 6", "4, 3, 2, 5, 4"),
    ("ENHANCED_CLRAD", "4200, 4500, 4000, 4300", "3800, 3600, 3400, 4000, 3700"),
    ("ENHANCED_DAMMULT", "0.58F, 0.55F, 0.62F, 0.57F", "0.60F, 0.65F, 0.70F, 0.58F, 0.62F"),
    ("ENHANCED_MAXMAG", "14000, 15000, 13000, 14500", "13500, 12500, 11500, 14000, 13000"),
    ("ENHANCED_DISHANDLE", "0.72F, 0.75F, 0.7F, 0.73F", "0.71F, 0.69F, 0.67F, 0.74F, 0.72F"),
    ("ENHANCED_OUTDAM", "0.7F, 0.75F, 0.68F, 0.72F", "0.69F, 0.65F, 0.62F, 0.73F, 0.70F"),
    ("ENHANCED_ENGINE", "3, 3, 3, 3", "3, 4, 4, 3, 3")
]

print("=== Replacement patterns for EnhancedStatList.java ===")
print()

for array_name, old_professional, new_elite in arrays_to_fix:
    print(f"// For {array_name}:")
    print(f"Replace:")
    print(f"        // Professional racers")
    print(f"        {old_professional}")
    print(f"    }};")
    print(f"With:")
    print(f"        // Professional racers")
    print(f"        {old_professional},")
    print(f"        ")
    print(f"        // Final 5 elite cars")
    print(f"        {new_elite}")
    print(f"    }};")
    print()
