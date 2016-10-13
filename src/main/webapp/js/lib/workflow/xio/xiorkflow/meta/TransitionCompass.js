function TransitionCompass() {
}
TransitionCompass.getOffset = function (fromMetaNodeModel, toMetaNodeModel) {
    if ((!fromMetaNodeModel) || (!toMetaNodeModel)) {
        return null;
    }

    //from rectangle left-down point location(fromX,fromY),right-up point location(fromMaxX,fromMaxY)
    var fromPoint = fromMetaNodeModel.getPosition();
    var fromX = fromPoint.getX();
    var fromY = fromPoint.getY();
    var fromSize = fromMetaNodeModel.getSize();
    var fromWidth = fromSize.getWidth();
    var fromHeight = fromSize.getHeight();
    var fromMinX = fromX;
    var fromMinY = fromY;
    var fromMaxX = fromX + fromWidth;
    var fromMaxY = fromY + fromHeight;

     //to rectangle left-down point location(toX,toY),right-up point location(toMaxX,toMaxY)
    var toPoint = toMetaNodeModel.getPosition();
    var toX = toPoint.getX();
    var toY = toPoint.getY();
    var toSize = toMetaNodeModel.getSize();
    var toWidth = toSize.getWidth();
    var toHeight = toSize.getHeight();
    var toMinX = toX;
    var toMinY = toY;
    var toMaxX = toX + toWidth;
    var toMaxY = toY + toHeight;
	
//	prompt("fromX",fromX);
//	prompt("fromY",fromY);
//	prompt("toX",toX);
//	prompt("toY",toY);
	//from under to
    if (fromMaxY <= toMinY) {
		//from cross to
        if ((fromMaxX >= toMinX) && (fromMinX <= toMaxX)) {
            var min = Math.max(fromMinX, toMinX);
            var max = Math.min(fromMaxX, toMaxX);
            var x = Math.round((min + max) / 2);
         // return [new Point(x - fromX, fromMaxY - fromY), new Point(x - toX, toMinY - toY)];
		  return [new Point(x - fromX, fromMaxY - fromY), new Point(toWidth/2, toMinY - toY)];
        } else {
            //from locates  right of to
			if (fromMinX > toMaxX) {
             //   return [new Point(fromMinX - fromX, fromMaxY - fromY), new Point(toMaxX - toX, toMinY - toY)];
			    return [new Point(fromMinX - fromX, fromMaxY - fromY), new Point(toMaxX - toX, toHeight/2)];
            } else {
                //from locates left of to
				if (fromMaxX < toMinX) {
                  //  return [new Point(fromMaxX - fromX, fromMaxY - fromY), new Point(toMinX - toX, toMinY - toY)];
				    return [new Point(fromMaxX - fromX, fromMaxY - fromY), new Point(toMinX - toX, toHeight/2)];
                }
            }
        }
    }

    //
    if (fromMinY >= toMaxY) {
        if ((fromMaxX >= toMinX) && (fromMinX <= toMaxX)) {
            var min = Math.max(fromMinX, toMinX);
            var max = Math.min(fromMaxX, toMaxX);
            var x = Math.round((min + max) / 2);
           // return [new Point(x - fromX, fromMinY - fromY), new Point(x - toX, toMaxY - toY)];
		    return [new Point(x - fromX, fromMinY - fromY), new Point(toWidth/2, toMaxY - toY)];
        } else {
            if (fromMinX > toMaxX) {
           //     return [new Point(fromMinX - fromX, fromMinY - fromY), new Point(toMaxX - toX, toMaxY - toY)];
		      return [new Point(fromMinX - fromX, fromMinY - fromY), new Point(toMaxX - toX, toHeight/2)];
            } else {
                if (fromMaxX < toMinX) {
             //       return [new Point(fromMaxX - fromX, fromMinY - fromY), new Point(toMinX - toX, toMaxY - toY)];
			  return [new Point(fromMaxX - fromX, fromMinY - fromY), new Point(toMinX - toX, toHeight/2)];
                }
            }
        }
    }

    //
    if ((fromMaxY > toMinY) && (fromMinY < toMaxY)) {
        var min = Math.max(fromMinY, toMinY);
        var max = Math.min(fromMaxY, toMaxY);
        var y = Math.round((min + max) / 2);
        if ((fromMaxX >= toMinX) && (fromMinX <= toMaxX)) {
            var min = Math.max(fromMinX, toMinX);
            var max = Math.min(fromMaxX, toMaxX);
            var x = Math.round((min + max) / 2);
          //  return [new Point(x - fromX, y - fromY), new Point(x - toX, y - toY)];
			 return [new Point(x - fromX, y - fromY), new Point(x - toX, toHeight/2)];
        } else {
            if (fromMinX > toMaxX) {
          //      return [new Point(fromMinX - fromX, y - fromY), new Point(toMaxX - toX, y - toY)];
				return [new Point(fromMinX - fromX, y - fromY), new Point(toMaxX - toX, toHeight/2)];
            } else {
                if (fromMaxX < toMinX) {
                    //return [new Point(fromMaxX - fromX, y - fromY), new Point(toMinX - toX, y - toY)];
					return [new Point(fromMaxX - fromX, y - fromY), new Point(toMinX - toX, toHeight/2)];
                }
            }
        }
    }

    //
    return [new Point(Math.round(fromWidth / 2), fromHeight), new Point(Math.round(toWidth / 2), 0)];
};
TransitionCompass.getFromOffset = function (fromMetaNodeModel, toMetaNodeModel) {
    if ((!fromMetaNodeModel) || (!toMetaNodeModel)) {
        return null;
    }

    //
    var size = fromMetaNodeModel.getSize();
    return new Point(size.getWidth() / 2, size.getHeight());
};
TransitionCompass.getToOffset = function (fromMetaNodeModel, toMetaNodeModel) {
    if ((fromMetaNodeModel == null) || (toMetaNodeModel == null)) {
        return null;
    }
    var size = toMetaNodeModel.getSize();
    return new Point(size.getWidth() / 2, 0);
};
TransitionCompass.convertOffsetToPoint = function (metaNodeModel, offset) {
    var point = metaNodeModel.getPosition();
    return new Point(point.getX() + offset.getX(), point.getY() + offset.getY());
};

