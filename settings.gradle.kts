
rootProject.name = "SMparser"
include("StepmaniaParser")
include("Common")
include("Player")
include("Player:SwingPlayer")
findProject(":Player:SwingPlayer")?.name = "SwingPlayer"
include("Player:DmxPlayer")
findProject(":Player:DmxPlayer")?.name = "DmxPlayer"
include("Player:OpenRgbPlayer")
findProject(":Player:OpenRgbPlayer")?.name = "OpenRgbPlayer"
