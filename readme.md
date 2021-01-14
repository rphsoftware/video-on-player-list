# Player List Video plugin

## Using

You will need the following: 
- ProtocolLib
- A 1.16.4 Paper server
- This plugin
- Some media, prepared following the guide below

After installing the plugin on the server, you will need the `playerlistimages.admin` permission node to use any administrative commands.

The following commands are available to everyone:

`/disableimages` - Stops you from seeing images on the player list  
`/enableimages` - Opts into images on the player list again  

The following commands are available to admins:

`/clearimage` - Stops any ongoing playback and clears the player list header  
`/showimage <name>` - Shows an image from the plugin's folder. The name is the file name, without the `.json` extension  
`/playvideo <name>` - Plays a video from the plugin's folder. The name is the file name without the `.zip` extension

### Adding new media

To make images for displaying, simply go to this [converter](https://rph.space/minecrafttext/image.html). It's a web app so you can just select an image and it will get converted in your browser.

To make videos, you need to either follow a video guide or the [text guide](https://rph.space/minecrafttext/video.html). The text guide will generate all commands you need to run on your computer to prepare the video for playback.


### Contributing

If you want to help with the plugin, you can join the [Discord server](https://discord.gg/uRRypMxST4) or the [Telegram group](https://t.me/froggumproductionsgroup).
After discussing your changes, open a pull request here.

### Compiling

If you want to compile the plugin from source, simply run `./gradlew build`