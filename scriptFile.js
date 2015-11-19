var JavaPackages = new JavaImporter(
Packages.m2.MyGame);
with(JavaPackages){
//var dynamicScriptOn = true;
game.setDynamicScriptOn(true);

//var isFullScreenMode = false;
game.setFullScreenMode(false);

//var camera1Left = 0.0;
//var camera1Right = 1.0;
//var camera1Bottom = 0.0;
//var camera1Top = 1.0;
game.setCamera1Left(0.0);
game.setCamera1Right(1.0);
game.setCamera1Bottom(0.0);
game.setCamera1Top(1.0);

//var skyboxScaleX = 40.0;
//var skyboxScaleY = 40.0;
//var skyboxScaleZ = 40.0;
game.setSkyboxScaleX(40.0);
game.setSkyboxScaleY(40.0);
game.setSkyboxScaleZ(40.0);

//var skyboxNorthPicture = "skybox/north.jpg";
//var skyboxEastPicture = "skybox/east.jpg";
//var skyboxSouthPicture = "skybox/south.jpg";
//var skyboxWestPicture = "skybox/west.jpg";
//var skyboxUpPicture = "skybox/up.jpg";
//var skyboxDownPicture = "skybox/down.jpg";
game.setSkyboxNorthPicture("skybox/north.jpg");
game.setSkyboxEastPicture("skybox/east.jpg");
game.setSkyboxSouthPicture("skybox/south.jpg");
game.setSkyboxWestPicture("skybox/west.jpg");
game.setSkyboxUpPicture("skybox/up.jpg");
game.setSkyboxDownPicture("skybox/down.jpg");

//var camera1DistanceFromTarget = 45.0;
//var camera1Azimuth = 180.0;
//var camera1Elevation = 65.0;
game.setCamera1DistanceFromTarget(45.0);
game.setCamera1Azimuth(180.0);
game.setCamera1Elevation(65.0);

//var width = 300;
//var gridWidth = 380;
//var transparentGridWidth = 301;
//var offset = 10;
game.setWidth(300);
game.setGridWidth(380);
game.setTransparentGridWidth(301);
game.setOffset(10);

//var player1SpawnX = 15.0;
//var player1SpawnY = 0.6;
//var player1SpawnZ = -5.0;
game.setPlayer1SpawnX(15.0);
game.setPlayer1SpawnY(.6);
game.setPlayer1SpawnZ(-5.0);

//var gridY = 1.0;
//var groundPlaneY = -1.0;
//var transparentPlaneY = 0.0;
//var ghostSpawnY = 0.5;
game.setGridY(1.0);
game.setGroundPlaneY(-1.0);
game.setTransparentPlaneY(0.0);
game.setGhostSpawnY(.5);
}