# ReactiveStepmaniaLightShow 

This code is very rough, there's still a fair bit of work to do until I am content putting this on mvncentral.

This is direct raw testing code straight to my goal, please don't judge me.

## TODOs

- [ ] Split off code for Stepmania parsing, OpenRGB and DMX driving into separate libs.
- [ ] Improve MainLoop so that it doesn't have to wait for the next frame.
- [x] Update LEDs per device instead of in bulk, so that the value is most actual before it is sent to OpenRGB.
- [ ] Clean up code, this is a mess.
- [ ] Add documentation
- [ ] Tests maybe?
- [ ] Make a YAML config to set up devices and its linking to lights

## Known issues
* The SM parser does not support stops yet.
* The theads don't stop when the song is done.
* It only supports `.sm` format for now.
* We can only play `.mp3` songs, no `.ogg`. Converting it seems to alter timings.
* ~~`ColorExtractor` is hilariously bad at its job. Primary, secondary and tertiary colors may be very off.~~
* Code is bad. I know.
* Despite wasting a lot of milliseconds, OpenRGB still might mis LED updates.

## Implementing this for your devices

For DMX, reimplement the `DmxMixer` class for your own setup.

For OpenRGB, implement the `Setup` class. See `TurboSetup` as example.

## Running

1. Download Etterna (Stepmania fork)
2. Use it to download songs
3. Reference your songs in Main.
4. Uncomment the code path for either `SwingMixer`, `DmxMixer` or your OpenRGB mixer.