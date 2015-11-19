var JavaPackages = new JavaImporter(
Packages.gameWorldObjects.TerrainManager,
Packages.scriptManagers.TerrainScriptManager);
with(JavaPackages){
terrMan.setSize(129);
terrMan.setIterations(2000);
terrMan.setMinRadius(5.0);
terrMan.setMaxRadius(20.0);
terrMan.setFlattening(2);
//setSeed cannot be used dynamically
terrMan.setSeed(12345);

terrMan.setXScale(1.0);
terrMan.setYScale(1.0);
terrMan.setZScale(1.0);
scriptMan.setMakeDynamic(true);

}