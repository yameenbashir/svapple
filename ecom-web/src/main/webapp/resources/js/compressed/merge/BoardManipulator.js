/*jshint undef: false, unused: false, indent: 2*/
/*global angular: false */


'use strict';

angular.module('AngularSpringApp').factory('BoardManipulator', function () {
  return {

    addColumn: function (board, columnName) {
      board.columns.push(new Column(columnName));
    },

    addCardToColumn: function (board, column, cardTitle, details
    		, id, schecduleId, userId,scheduleName, 
    		startDate, startTime, endDate, endTime, day) {
    	 alert(cardDetails.day);
      angular.forEach(board.columns, function (col) {
        if (col.name === column.name) {
          col.cards.push(new Card(cardTitle, column.name, details, id, schecduleId, userId,scheduleName, 
          		startDate, startTime, endDate, endTime, day));
        }
      });
    },
    addNewCard: function (board, column) {
        var modalInstance = $uibModal.open({
          templateUrl: 'resources/pages/newCard.html',
          controller: 'NewCardController',
          backdrop: 'static',
          resolve: {
            column: function () {
              return column;
            }
          }
        });
        modalInstance.result.then(function (cardDetails) {
          if (cardDetails) {
        	 
            BoardManipulator.addCardToColumn(board, cardDetails.column, cardDetails.title, cardDetails.details
            		, cardDetails.id, cardDetails.schecduleId, cardDetails.userId, cardDetails.scheduleName, 
            		cardDetails.startDate, cardDetails.startTime, cardDetails.endDate, cardDetails.endTime, cardDetails.day,
            		cardDetails.workplaceId,cardDetails.workplaceName,cardDetails.departmentId,cardDetails.departmentName,cardDetails.employeeWorkScheduleId);
          }
        });
      },
    removeCardFromColumn: function (board, column, card) {
      angular.forEach(board.columns, function (col) {
        if (col.name === column.name) {
          col.cards.splice(col.cards.indexOf(card), 1);
        }
      });
    },
    addBacklog: function (board, backlogName, id, name,css,img) {
    	 
      board.backlogs.push(new Backlog(backlogName,id,name,css,img));
      
    },

    addPhaseToBacklog: function (board, backlogName, phase) {
      angular.forEach(board.backlogs, function (backlog) {
    	 if (backlog.name === backlogName) {
          backlog.phases.push(new Phase(phase.name));
        }
      });
    },

    addCardToBacklog: function (board, backlogName, phaseName, task) {
      angular.forEach(board.backlogs, function (backlog) {
        if (backlog.name === backlogName) {
          angular.forEach(backlog.phases, function (phase) {
            if (phase.name === phaseName) {
              phase.cards.push(new Card(task.title, task.status, task.details
            		  , task.id, task.schecduleId, task.userId, task.scheduleName, 
              		task.startDate, task.startTime, task.endDate, task.endTime, task.day,
              		task.workplaceId,task.workplaceName,task.departmentId,task.departmentName,task.employeeWorkScheduleId));
            }
          });
        }
      });
    }
  };
});
