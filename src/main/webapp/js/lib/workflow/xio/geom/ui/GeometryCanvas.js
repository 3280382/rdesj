function GeometryCanvas() {
    this.base = Panel;
    this.base(Toolkit.newElement("v:group"));
    this.setPosition("relative");
}
GeometryCanvas.prototype = new Panel();

