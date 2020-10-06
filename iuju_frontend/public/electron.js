const electron = require('electron');
const app = electron.app;
const BrowserWindow = electron.BrowserWindow;
const Tray = electron.Tray;
const path = require('path');
const isDev = require('electron-is-dev');
const iconPath = path.join(__dirname,'/logo192.png');
const Menu = electron.Menu;
const globalShortcut = electron.globalShortcut;
const ipc = electron.ipcMain;
const dialog = electron.dialog;
const Notification = electron.Notification;
let mainWindow, win, eventWin, taskWin, noteWin;
const x_right= 1400
const y_right= 300
const width_window_right = 500
const height_window_right = 600
const height_window_popup = 200

function createWindow() {
  const tray = new Tray(iconPath);

  // mainWindow
  mainWindow = new BrowserWindow({width: 900, height: 680, webPreferences: {
    nodeIntegration: true, webSecurity: false
  }});
  mainWindow.loadURL(isDev ? 'http://localhost:3000' : `file://${path.join(__dirname, '../build/index.html')}`);

  // popUp Window
  win = new BrowserWindow({ show:false, parent:mainWindow, modal: true, frame: false, width: width_window_right, height: height_window_popup, title: 'Iuju!', webPreferences: {
    nodeIntegration: true, webSecurity: false
  }})
  win.setPosition(x_right,850, true);
  win.loadURL(isDev ? 'http://localhost:3000/recorder' : `file://${path.join(__dirname, '../build/index.html')}`);

  // Event window
  eventWin = new BrowserWindow({ show:false, parent:mainWindow, modal: true, frame: true, width: width_window_right, height: height_window_right, title: 'Iuju!' , webPreferences: {
    nodeIntegration: true , webSecurity: false}})
  eventWin.setPosition(x_right, y_right, true);
  eventWin.loadURL('http://localhost:3000/newevent')

  // Note window
  noteWin = new BrowserWindow({ show:false, parent:mainWindow, modal: true, frame: false, width: width_window_right, height: height_window_right, title: 'Iuju!' , webPreferences: {
    nodeIntegration: true , webSecurity: false}})
  noteWin.setPosition(x_right, y_right, true);
  noteWin.loadURL('http://localhost:3000/newnote')

  // Task window
  taskWin = new BrowserWindow({ show:false, parent:mainWindow, modal: true, frame: false, width: width_window_right, height: height_window_right, title: 'Iuju!' , webPreferences: {
    nodeIntegration: true , webSecurity: false}})
  taskWin.setPosition(x_right, y_right, true);
  taskWin.loadURL('http://localhost:3000/newtask')

  if (isDev) {
    // Open the DevTools.
    //BrowserWindow.addDevToolsExtension('<location to your react chrome extension>');
    //mainWindow.webContents.openDevTools();
  }
  mainWindow.on('closed', () => {
      
    //mainWindow = null
      
    });

    mainWindow.on('close', function (event) {
        if(!app.isQuiting){
            event.preventDefault();
            var contextMenu = Menu.buildFromTemplate([
                { label: 'Show Iuju!', click:  function(){
                    mainWindow.show();
                } },
                { label: 'Quit', click:  function(){
                    app.isQuiting = true;
                    app.quit();
                } }
            ]);
            tray.setContextMenu(contextMenu);
            tray.setToolTip("Iuju!")
            mainWindow.hide();
        }
    
        return false;
    });

    globalShortcut.register('Alt+1', function(){
      mainWindow.show();
    })
    globalShortcut.register('Alt+2', function(){
      if(win.isVisible()){
        win.hide();
      }
      else{
        win.show();
      }
      
    })
}

app.on('ready', createWindow);

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});


app.on('activate', () => {
  if (mainWindow === null) {
    createWindow();
  }
});

app.commandLine.appendSwitch('disable-features', 'OutOfBlinkCors');

ipc.on('open-event-extended', function(event){
  eventWin.show()
  win.hide()
})

ipc.on('hide-popup-window', function(event){
  win.hide();
})

ipc.on('hide-window', function(event){
  eventWin.hide();
})
ipc.on('open-task-extended', function(event){
  win = new BrowserWindow({ show:false, parent:mainWindow, modal: true, frame: false, width:400, height: 600, title: 'Iuju!' })
  win.loadURL(isDev ? 'http://localhost:3000/task' : `file://${path.join(__dirname, '../build/index.html')}`);
})

ipc.on('open-note-extended', function(event){
  win = new BrowserWindow({ show:false, parent:mainWindow, modal: true, frame: false, width:400, height: 600, title: 'Iuju!' })
  win.loadURL(isDev ? 'http://localhost:3000/note' : `file://${path.join(__dirname, '../build/index.html')}`);
})

ipc.on('send-notification-event', function(event, arg){
  new Notification({title: 'Eventos del Día', body: arg}).show()
  event.sender.send('sended-notification-event', 'notificado')
})