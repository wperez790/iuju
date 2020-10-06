/*Trigger insert en Medico*/
/*Cada vez que se inserta un medico, significa que se debe crear una agenda para ese medico .*/
Drop trigger if exists triggerInsertMedico;
DELIMITER //

CREATE TRIGGER triggerInsertMedico AFTER 
INSERT ON medico
FOR EACH ROW 
BEGIN 
INSERT INTO agenda (descripcion, medico_id, tipo) VALUES ('descripcion', new.id_medico, 0);
INSERT INTO agenda (descripcion, medico_id, tipo) VALUES ('descripcion', new.id_medico, 1);
END//
DELIMITER ;

/*Trigger insert en Agenda*/
/*Cada vez que se inserta una agenda, significa que se van a setear fechas por defecto para esa agenda.*/
Drop trigger if exists triggerInsertAgenda;
DELIMITER //

CREATE TRIGGER triggerInsertAgenda AFTER 
INSERT ON agenda
FOR EACH ROW 
BEGIN 
INSERT INTO fecha (dia, hora_fin, hora_inicio, agenda_id, activo, day_of_week) VALUES ('lunes', "19:00:00","11:00:00", new.id_agenda , 0, 2);
INSERT INTO fecha (dia, hora_fin, hora_inicio, agenda_id, activo, day_of_week) VALUES ('martes', "19:00:00","11:00:00", new.id_agenda , 0, 3);
INSERT INTO fecha (dia, hora_fin, hora_inicio, agenda_id, activo, day_of_week) VALUES ('miércoles', "19:00:00","11:00:00", new.id_agenda , 0, 4);
INSERT INTO fecha (dia, hora_fin, hora_inicio, agenda_id, activo, day_of_week) VALUES ('jueves', "19:00:00","11:00:00", new.id_agenda , 0, 5);
INSERT INTO fecha (dia, hora_fin, hora_inicio, agenda_id, activo, day_of_week) VALUES ('viernes', "19:00:00","11:00:00", new.id_agenda , 0, 6);
INSERT INTO fecha (dia, hora_fin, hora_inicio, agenda_id, activo, day_of_week) VALUES ('sábado', "19:00:00","11:00:00", new.id_agenda , 0, 7);
INSERT INTO fecha (dia, hora_fin, hora_inicio, agenda_id, activo, day_of_week) VALUES ('domingo', "19:00:00","11:00:00", new.id_agenda , 0, 1);

END//
DELIMITER ;


/*Trigger update en Fecha*/
/*Cada vez que se realiza una actualización en la fecha, genero turnos libres de aca a un mes en ese dia.*/

/*Drop trigger if exists triggerUpdateFecha;
DELIMITER //

CREATE TRIGGER triggerUpdateFecha AFTER 
UPDATE ON fecha
FOR EACH ROW 
BEGIN 
declare tipo INT;
declare id_m INT;

SET tipo = (SELECT tipo FROM agenda where id_agenda = new.agenda_id);
SET id_m = (SELECT medico_id FROM agenda where id_agenda = new.agenda_id);

#IF new.activo = 1 then
  CALL generarTurnosDelMes(new.day_of_week, new.hora_inicio, new.hora_fin, id_m, tipo);
  #CALL eliminarTurnosDelMesNoValidos(new.dia, new.hora_inicio, new.hora_fin, id_m);
#END IF ;
END//
DELIMITER ;
*/
UPDATE fecha SET hora_inicio="10:00", hora_fin="12:00", activo = 1 where id_fecha = 4;
CALL generarTurnosDelMes("lunes", TIME("10:00"), TIME("12:00"), 2, 0);
SET @@lc_time_names = 'es_AR';
SELECT ("lunes" LIKE DAYNAME(now()+ interval 8 day))

/*Procedimiento para generar turnos del mes en ese dia y franja horaria*//*Procedimiento para crear primeros turnos libres de aca a un mes en el horario correspondiente*/
DELIMITER //
drop procedure if exists generarTurnosDelMes //
create procedure generarTurnosDelMes(IN day_ow INT, IN hora_inicio TIME, IN hora_fin TIME, IN id_m INT, IN tipo INT)
BEGIN
declare idTurno INT;
declare i INT;
declare m INT;
declare max INT;
declare j TIME;
declare fecha_aux DATE;
declare diaFecha DATE;
declare fecha_fin_aux DATETIME;
declare fecha_inicio_aux DATETIME;
declare hora_inicio_aux TIME;
declare crearTurnos bool;
declare hours TIME;

SET diaFecha = null;
SET hora_inicio_aux = hora_inicio;
SET crearTurnos = false;
SET i = 0;
SET m = 7;
SET hours = HOUR(TIMEDIFF(hora_fin, hora_inicio)) ;
SET j = 0;
#IF hours = 0 then
#  SET max = 3;
#ELSE
#  SET max = hours * 60 / 20; #60 minutos / 20minutos x turno
#END IF;

label1: while i < m do
SET fecha_aux = DATE(now() + interval i day);
SET i = i + 1;
IF (day_ow = DAYOFWEEK(fecha_aux)) then
  SET diaFecha = fecha_aux ;
ELSE
  iterate label1;
END IF;
END WHILE;


label2: while hora_inicio_aux < hora_fin and diaFecha is not null do
 
SET fecha_inicio_aux = cast(concat(diaFecha, ' ', hora_inicio_aux) as datetime); #cast(diaFecha as DATETIME) + cast( hora_inicio_aux as DATETIME);
SET fecha_fin_aux = cast(concat(diaFecha, ' ', hora_inicio_aux + interval 20 MINUTE) as datetime); #cast(diaFecha as DATETIME) + cast( (hora_inicio_aux + interval 20 Minute) as DATETIME);

INSERT INTO turno (fecha_fin, fecha_inicio, medico_id, descripcion, estado, tipo, activo) VALUES (fecha_fin_aux, fecha_inicio_aux, id_m, "Turno libre autogenerado", 2, tipo, 1);
INSERT INTO turno (fecha_fin, fecha_inicio, medico_id, descripcion, estado, tipo, activo) VALUES (fecha_fin_aux + INTERVAL 7 DAY, fecha_inicio_aux + INTERVAL 7 DAY, id_m, "Turno libre autogenerado", 2, tipo, 1);
INSERT INTO turno (fecha_fin, fecha_inicio, medico_id, descripcion, estado, tipo, activo) VALUES (fecha_fin_aux + INTERVAL 14 DAY, fecha_inicio_aux + INTERVAL 14 DAY, id_m, "Turno libre autogenerado", 2, tipo, 1);
INSERT INTO turno (fecha_fin, fecha_inicio, medico_id, descripcion, estado, tipo, activo) VALUES (fecha_fin_aux + INTERVAL 21 DAY, fecha_inicio_aux + INTERVAL 21 DAY, id_m, "Turno libre autogenerado", 2, tipo, 1);
#INSERT INTO turno (fecha_fin, fecha_inicio, medico_id, descripcion, estado, tipo, activo) VALUES (fecha_fin_aux + INTERVAL 28 DAY, fecha_inicio_aux + INTERVAL 28 DAY, id_m, "Turno libre autogenerado", 2, tipo, 1);
SET hora_inicio_aux = hora_inicio_aux + interval 20 MINUTE;
#SET j = j + 1;
END WHILE;
SELECT * FROM turno;
END//
DELIMITER ;


/*SCHEDULE AN EVENT*/
DROP EVENT if exists marcarAtendidoEvento;
CREATE EVENT marcarAtendidoEvento
    ON SCHEDULE EVERY 10 MINUTE
    DO
      CALL marcarAtendido();

/*Procedimiento para marcar como atendido cuando pasa el horario*/
DELIMITER //
drop procedure if exists marcarAtendido //
create procedure marcarAtendido()
BEGIN
declare gananciaViaje FLOAT;
declare idTurno INT;
declare i INT;
declare m INT;


DROP TABLE if exists tableAux;
CREATE TABLE tableAux (id_tableAux INT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id_tableAux), KEY(id_turno)) SELECT * FROM turno where fecha_fin < now() and (estado = 2 or estado = 1 );

SET m = (SELECT count(1) FROM tableAux);
SET i = 1;
label1: while i <= m do

SET idTurno = (SELECT id_turno from tableAux where id_tableAux = i); 
UPDATE turno SET estado = 3 where id_turno = idTurno;
SET i= i+1;

END WHILE;
END//
DELIMITER ;


/*Procedimiento para eliminar turnos del mes no validos cuando se cambia de horario.*/
DELIMITER //
drop procedure if exists eliminarTurnosDelMesNoValidos //
create procedure eliminarTurnosDelMesNoValidos(IN id_fecha INT, IN day_of_week INT, IN hora_inicio TIME, IN hora_fin TIME,  IN id_m INT, IN tipo INT)
BEGIN
declare idFecha INT;
declare activo_ bool;
declare idAgenda INT;
/*declare diaWeek INT;
declare h_inicio TIME;
declare h_fin TIME;*/

SET idAgenda = (SELECT id_agenda FROM agenda where medico_id = id_m and tipo = tipo);
SET activo_ = (SELECT activo FROM fecha where id_fecha = id_fecha);
IF (activo_ = true) then
DELETE FROM turno where id_turno IN (SELECT id_turno from turno where medico_id = id_m AND dayofweek(fecha_inicio) = day_of_week AND TIME(fecha_inicio) < hora_inicio AND tipo = tipo AND estado = 2);
DELETE FROM turno where id_turno IN (SELECT id_turno from turno where medico_id = id_m AND dayofweek(fecha_fin) = day_of_week AND TIME(fecha_fin) > hora_fin AND tipo = tipo AND estado = 2);
ELSE 
DELETE FROM turno where id_turno IN (SELECT * from turno where medico_id = id_m AND dayofweek(fecha_inicio) = day_of_week and estado = 2);
END IF;
END//
DELIMITER ;

show triggers;

CALL eliminarTurnosDelMesNoValidos(25,2, TIME("12:00"), TIME("13:00"), 30, 0);
(SELECT id_fecha FROM fecha where day_of_week = 2 AND agenda_id = 6);