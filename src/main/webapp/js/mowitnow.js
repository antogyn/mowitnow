drawModule = function() {

  var solution = null;
  var svg = null;
  var width = null;
  var height = null;
  var maxWidth = 450;
  var maxHeight = 650;
  var xScale = null;
  var yScale = null;
  var cellSideLength = null;
  var gField = null;
  var gMowers = null;
  var tooltip = null;

  // points in a cell
  var north = null;
  var east = null;
  var south = null;
  var west = null;

  var scaledPoint = function(px, py) {
    var x = cellSideLength * px;
    var y = cellSideLength * py;
    var toString = function() {
      return (x + "," + y);
    };
    return toString;
  };
  
  var drawBorders = function() {
    svg = d3.select("#svg-container").append("svg")
    .attr("width", maxWidth)
    .attr("height", maxWidth)
    .style("border", "1px solid black");
  }

  var drawSolution = function(solutionJson) {
    solution = JSON.parse(solutionJson);
    if (!(solution.hasOwnProperty("width"))) {
      // error
      $("#output").text(solution);
    } else {
      draw();
      addToOutput();
    }
  };
  
  var addToOutput = function() {
    $("#output").text(solution.finalSolution);
  };

  var draw = function() {
    initSvg();
    initSlider();
    drawField();
    drawMowersSequence();
  };

  var drawMowersSequence = function() {
    $slider = $('#slider-container');
    $slider.slider("value", 0);
    $slider.trigger("slidechange");
    var i = 1;
    (function nextStep() {
      setTimeout(function() {
        if (i <= solution.mowersAtStep.length - 1) {
          $slider.slider("value", i);
          $slider.trigger("slidechange");
          i++;
          nextStep();
        }
      }, 250);
    })();
  };

  var initSvg = function() {
    // we only use the width because we don't mind scrolling vertically but
    // not horizontally and we want squares
    xScale = d3.scale.linear().domain([ 0, solution.width + 1 ]).range(
        [ 0, maxWidth ]);
    // reversed y-axis (down/up)
    height = xScale(solution.height + 1);
    
    if (height > maxHeight) {
      xScale = d3.scale.linear().domain([ 0, solution.height + 1 ]).range(
          [ 0, maxWidth ]);
      height = xScale(solution.height + 1);
    }
    
    width = xScale(solution.width + 1);

    yScale = d3.scale.linear().domain([ -1, solution.height ]).range(
        [ height, 0 ]);
    
    $("#svg-container").empty();

    svg = d3.select("#svg-container").append("svg")
      .attr("width", width)
      .attr("height", height)
      .style("border", "1px solid black");

    gField = svg.append("g").attr("id", "gField");

    gMowers = svg.append("g").attr("id", "gMowers");

    cellSideLength = xScale(1);

    north = scaledPoint(0.5, 0);
    east = scaledPoint(1, 0.5);
    south = scaledPoint(0.5, 1);
    west = scaledPoint(0, 0.5);
    
    tooltip = d3.select("body").append("div")   
      .attr("class", "tooltip")               
      .style("opacity", 0);
  };

  var initSlider = function() {
    $("#slider-container").slider({
      value : 0,
      min : 0,
      max : solution.mowersAtStep.length - 1,
      step : 1,
      width : width,
      // we have to attach this event to trigger it manually
      // (because of a bug in jquery-ui)
      change : function(event, ui) {
        drawMowers(ui.value);
      },
      slide : function(event, ui) {
        drawMowers(ui.value);
      }
    });
  };

  var drawField = function() {

    for (var i = 0; i <= solution.width; i++) {
      for (var j = 0; j <= solution.height; j++) {
        // it's useless to bind the field since it won't change
        gField.append("rect")
          .attr("x", xScale(i))
          .attr("y", yScale(j))
          .attr("coord", "(" + i + "," + j + ")")
          .attr("width", cellSideLength)
          .attr("height", cellSideLength)
          .attr("fill", "#66CC66")
          .attr("stroke-width", 1)
          .attr("stroke", 'black')
          .on("mouseover", function() {
            var that = d3.select(this);
            var absoluteMousePos = d3.mouse(d3.select('body').node());
            tooltip
              .html(that.attr("coord"))
              .style({
                opacity: .9,
                left: (absoluteMousePos[0] + 30)+'px',
                top: (absoluteMousePos[1])+'px'
              });  
            that.attr("fill", "#9fdf9f");
           })
           .on('mousemove', function() {
              var absoluteMousePos = d3.mouse(d3.select('body').node());
              tooltip.style({
                left: (absoluteMousePos[0] + 30)+'px',
                top: (absoluteMousePos[1])+'px'
              });
           })
          .on("mouseout", function() {
            var that = d3.select(this);
            tooltip.style("opacity", 0);
            that.attr("fill", "#66CC66");
          });
      }
    }
  };

  var drawMowers = function(step) {

    mowers = solution.mowersAtStep[step];

    var mowersGroup = gMowers.selectAll("g").data(mowers, function(mower) {
      return mower.id;
    });

    // exit
    mowersGroup.exit().remove();

    // enter
    var mowersGroupEnter = mowersGroup.enter().append("g")
      .on("mouseover", function() {
         var that = d3.select(this);
         var absoluteMousePos = d3.mouse(d3.select('body').node());
         tooltip
           .html(that.attr("representation"))
           .style({
             opacity: .9,
             left: (absoluteMousePos[0] + 30)+'px',
             top: (absoluteMousePos[1])+'px'
           });  
         that.selectAll("rect").attr("fill", "#ff6767");
        })
        .on('mousemove', function() {
           var absoluteMousePos = d3.mouse(d3.select('body').node());
           tooltip.style({
             left: (absoluteMousePos[0] + 30)+'px',
             top: (absoluteMousePos[1])+'px'
           });
        })
       .on("mouseout", function() {
         var that = d3.select(this);
         tooltip.style("opacity", 0);
         that.selectAll("rect").attr("fill", "red");
       });

    // update
    mowersGroup
      .attr("representation", function(d) {return "(" + d.coordinate.x + "," + d.coordinate.y + "," + d.direction.charAt(0) + ")";})
      .transition()
      .attr(
        "transform",
        function(mower) {
          return "translate(" + xScale(mower.coordinate.x) + ","
              + yScale(mower.coordinate.y) + ")";
        });

    // enter
    mowersGroupEnter.append("rect")
      .attr('x', 0)
      .attr('y', 0)
      .attr('width', cellSideLength)
      .attr('height', cellSideLength)
      .attr("fill", "red")
      .attr('stroke-width', 1)
      .attr('stroke', 'black');

    // enter
    mowersGroupEnter.append("polygon")
      .style("stroke", "black")
      .style("fill", "none");

    // update
    mowersGroup.select("polygon")
      .datum(function(mower) {
        return mower;
      })
      .transition()
      .attr("points", function(mower) {
        return getTriangleFromDirection(mower.direction);
      });

  };

  var getTriangleFromDirection = function(direction) {
    switch (direction) {
    case "NORTH":
      return west() + "," + north() + "," + east();
      break;
    case "EAST":
      return north() + "," + east() + "," + south();
      break;
    case "SOUTH":
      return east() + "," + south() + "," + west();
      break;
    case "WEST":
      return south() + "," + west() + "," + north();
      break;
    default:
      return "error";
      break;
    }
  };

  return {
    drawBorders : drawBorders,
    drawSolution : drawSolution
  };

}();

ajaxModule = function() {
  
  var init = function() {
    $("#button-input").click(function() {
      postInput($("#input").val());
    });
    $("#input").text(
          "5 5\n"
        + "1 2 N\n"
        + "GAGAGAGAA\n"
        + "3 3 E\n"
        + "AADAADADDA"
    );
  };
  
  var postInput = function(input) {
    var input = $("#input").val();
    $.ajax({
      url: "./Mow",
      type: "POST",
      data: {
          input: input
      },
      cache: false,
      success: function(json) {
        drawModule.drawSolution(json);
      },
      error: function(e) {
        $("#output").text("An unexpected error occured : " + e.message);
      }
    });
  };
  
  return {
    init : init
  };
  
}();

$(function() {
  ajaxModule.init();
  drawModule.drawBorders();
});