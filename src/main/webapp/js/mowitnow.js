drawModule = function() {

  var solution = null;
  var svg = null;
  var width = 400;
  var height = 400;
  var maxWidth = 400;
  var maxHeight = 500;
  var xScale = null;
  var yScale = null;
  var cellSideLength = null;
  var gField = null;
  var gMowers = null;
  var tooltip = null;
  var sequenceTimeout = null;

  // points in a cell
  var north = null;
  var east = null;
  var south = null;
  var west = null;

  var scaledPoint = function(px, py) {
    var x = cellSideLength * px;
    var y = cellSideLength * py;
    var toString = function() {
      return (x + ',' + y);
    };
    return toString;
  };

  var drawEmpty = function() {
    $('#svg-container').empty();
    
    svg = d3.select('#svg-container').append('svg')
      .attr('id', 'svg')
      .attr('width', 400)
      .attr('height', 400)
      .attr('viewBox', '0 0 ' + width + ' ' + height + '')
      .attr('preserveAspectRatio', 'xMinYMin')
      .style('border', '1px solid darkgrey');

    svg.append('g')
      .append('rect')
      .attr('x', 0)
      .attr('y', 0)
      .attr('width', width)
      .attr('height', height)
      .attr('fill', '#fff');

    makeResponsive();
  }

  var drawSolution = function(solutionJson) {
    solution = JSON.parse(solutionJson);
    if (solution.hasOwnProperty('error')) {
      $('#output').text(solution.error);
    } else {
      addToOutput();
      draw();
    }
  };

  var draw = function() {
    if (solution.width * solution.height <= 10000) { 
      initScales();
      initSvg();
      makeResponsive();
      drawField();
      drawMowersSequence(0);
    } else {
      drawEmpty();
      svg.append('text')
        .attr('x', 20)
        .attr('y', 40)
        .text('The field is too big to be drawn (10000 cells max).');
    }
  };

  var makeResponsive = function() {
    var $svg = $('#svg');
    var $controls = $('#controls');
    $(window).on('resize', function() {
      var targetWidth = $svg.parent().width();
      // if it's too big
      var targetHeight = targetWidth * height / width;

      if (targetHeight > maxHeight) {
        targetHeight = maxHeight
        targetWidth = targetHeight * width / height;
      }
      $svg.attr('width', targetWidth).attr('height', targetHeight);
      $controls.css('width', targetWidth);
    });

    $(window).trigger('resize');
  };

  var addToOutput = function() {
    $('#output').text(solution.finalSolution);
  };

  var drawMowersSequence = function(start) {
    $slider = $('#slider-container');

    $slider.slider('option', 'max', solution.mowersAtStep.length - 1);

    $slider.slider('value', start);
    var i = start+1;
    (function nextStep() {
      sequenceTimeout = setTimeout(function() {
        if (i <= solution.mowersAtStep.length - 1) {
          $slider.slider('value', i);
          i++;
          nextStep();
        }
      }, 250);
    })();
  };

  var initScales = function() {
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

    cellSideLength = xScale(1);

    north = scaledPoint(0.5, 0);
    east = scaledPoint(1, 0.5);
    south = scaledPoint(0.5, 1);
    west = scaledPoint(0, 0.5);
  };

  var initSvg = function() {

    $('#svg-container').empty();

    svg = d3.select('#svg-container')
      .append('svg')
      .attr('id', 'svg')
      .attr('width', width)
      .attr('height', height)
      .attr('viewBox', '0 0 ' + width + ' ' + height + '')
      .attr('preserveAspectRatio', 'xMinYMin');

    gField = svg.append('g').attr('id', 'gField');

    gMowers = svg.append('g').attr('id', 'gMowers');

    // borders
    svg.append('rect')
      .attr('x', 0)
      .attr('y', 0)
      .attr('width', width)
      .attr('height', height)
      .attr('fill', 'none')
      .attr('stroke-width', 3)
      .attr('stroke', 'black');

    tooltip = d3.select('body').append('div')
      .attr('class', 'tooltip')
      .style('opacity', 0);

  };

  var initControls = function() {
    var $slider = $('#slider-container');

    $slider.slider({
      value : 0,
      min : 0,
      max : 1,
      step : 1,
      // we have to attach this event to trigger it manually
      change : function(event, ui) {
        clearTimeout(sequenceTimeout);
        drawMowers(ui.value);
      },
      slide : function(event, ui) {
        clearTimeout(sequenceTimeout);
        drawMowers(ui.value);
      }
    });

    $('#first').click(function() {
      $slider.slider('value', $slider.slider('option', 'min'));
    })

    $('#prev').click(function() {
      $slider.slider('value', $slider.slider('value') - 1);
    })
    
     $('#play').click(function() {
       drawMowersSequence($slider.slider('value') + 1);
     })

    $('#next').click(function() {
      $slider.slider('value', $slider.slider('value') + 1);
    })

    $('#last').click(function() {
      $slider.slider('value', $slider.slider('option', 'max'));
    })

  };

  var drawField = function() {

    for (var i = 0; i <= solution.width; i++) {
      for (var j = 0; j <= solution.height; j++) {
        // it's useless to bind the field since it won't change
        gField.append('rect')
          .attr('x', xScale(i))
          .attr('y', yScale(j))
          .attr('coord', '(' + i + ',' + j + ')')
          .attr('width', cellSideLength)
          .attr('height', cellSideLength)
          .attr('fill', '#66CC66')
          .attr('stroke-width', 1)
          .attr('stroke', 'black')
          .on('mouseover', function() {
            var that = d3.select(this);
            var absoluteMousePos = d3.mouse(d3.select('body').node());
            tooltip.html(that.attr('coord')).style({
              opacity : .9,
              left : (absoluteMousePos[0] + 30) + 'px',
              top : (absoluteMousePos[1]) + 'px'
            });
            that.attr('fill', '#9fdf9f');
          })
          .on('mousemove', function() {
            var absoluteMousePos = d3.mouse(d3.select('body').node());
            tooltip.style({
              left : (absoluteMousePos[0] + 30) + 'px',
              top : (absoluteMousePos[1]) + 'px'
            });
          })
          .on('mouseout', function() {
            var that = d3.select(this);
            tooltip.style('opacity', 0);
            that.attr('fill', '#66CC66');
          });
      }
    }
  };

  var drawMowers = function(step) {

    mowers = solution.mowersAtStep[step];

    var mowersGroup = gMowers.selectAll('g').data(mowers, function(mower) {
      return mower.id;
    });

    // exit
    mowersGroup.exit().remove();

    // enter
    var mowersGroupEnter = mowersGroup.enter().append('g')
      .on('mouseover', function() {
        var that = d3.select(this);
        var absoluteMousePos = d3.mouse(d3.select('body').node());
        tooltip.html(that.attr('representation')).style({
          opacity : .9,
          left : (absoluteMousePos[0] + 30) + 'px',
          top : (absoluteMousePos[1]) + 'px'
        });
        that.selectAll('rect').attr('fill', '#ff6767');
      })
      .on('mousemove', function() {
        var absoluteMousePos = d3.mouse(d3.select('body').node());
        tooltip.style({
          left : (absoluteMousePos[0] + 30) + 'px',
          top : (absoluteMousePos[1]) + 'px'
        });
      })
      .on('mouseout', function() {
        var that = d3.select(this);
        tooltip.style('opacity', 0);
        that.selectAll('rect').attr('fill', 'red');
      });

    // update
    mowersGroup
      .attr('representation', function(d) {
        return '(' + d.coordinate.x + ',' + d.coordinate.y + ','
          + d.direction.charAt(0) + ')';
        })
      .transition()
      .attr('transform', function(mower) {
        return 'translate(' + xScale(mower.coordinate.x) + ','
          + yScale(mower.coordinate.y) + ')';
       });

    // enter
    mowersGroupEnter.append('rect')
      .attr('x', 0)
      .attr('y', 0)
      .attr('width', cellSideLength)
      .attr('height', cellSideLength)
      .attr('fill', 'red')
      .attr('stroke-width', 1)
      .attr('stroke', 'black');

    // enter
    mowersGroupEnter.append('polygon')
      .style('stroke', 'black')
      .style('fill', 'none');

    // update
    mowersGroup.select('polygon')
      .datum(function(mower) {
        return mower;
      })
      .transition()
      .attr('points', function(mower) {
        return getTriangleFromDirection(mower.direction);
      });
  };

  var getTriangleFromDirection = function(direction) {
    switch (direction) {
    case 'NORTH':
      return west() + ',' + north() + ',' + east();
      break;
    case 'EAST':
      return north() + ',' + east() + ',' + south();
      break;
    case 'SOUTH':
      return east() + ',' + south() + ',' + west();
      break;
    case 'WEST':
      return south() + ',' + west() + ',' + north();
      break;
    default:
      return 'error';
      break;
    }
  };

  return {
    drawEmpty : drawEmpty,
    drawSolution : drawSolution,
    initControls : initControls
  };

}();

ajaxModule = function() {

  var init = function() {
    $('#button-input').click(function() {
      postInput($('#input').val());
    });
    $('#input').text(
        '5 5\n' + '1 2 N\n' + 'GAGAGAGAA\n' + '3 3 E\n' + 'AADAADADDA');
  };

  var postInput = function(input) {
    var input = $('#input').val();
    $.ajax({
      url : './Mow',
      type : 'POST',
      data : {
        input : input
      },
      cache : false,
      success : function(json) {
        drawModule.drawSolution(json);
      },
      error : function(e) {
        $('#output').text('An unexpected error occured : ' + e.message);
      }
    });
  };

  return {
    init : init
  };

}();

$(function() {
  ajaxModule.init();
  drawModule.initControls();
  drawModule.drawEmpty();
});