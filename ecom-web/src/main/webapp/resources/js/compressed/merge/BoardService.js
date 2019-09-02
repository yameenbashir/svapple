/*jshint undef: false, unused: false, indent: 2*/
/*global angular: false */

'use strict';

angular.module('AngularSpringApp').service('BoardService', ['$uibModal', 'BoardManipulator', function ($uibModal, BoardManipulator) {

  return {
    
    sprintBoard: function (board) {
      var sprintBoard = new Board(board.name, board.numberOfColumns);
      angular.forEach(board.columns, function (column) {
        BoardManipulator.addColumn(sprintBoard, column.name);
      });
      angular.forEach(board.backlogs, function (backlog) {
    	
    	 BoardManipulator.addBacklog(sprintBoard, backlog.title,backlog.userId,backlog.userName, backlog.css,backlog.img);
        angular.forEach(backlog.phases, function (phase) {
          BoardManipulator.addPhaseToBacklog(sprintBoard, backlog.title, phase);
          angular.forEach(phase.cards, function (card) {
            BoardManipulator.addCardToBacklog(sprintBoard, backlog.title, phase.name, card);
          });
        });

      });
      return sprintBoard;
    }
  };
}]);