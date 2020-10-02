const net = require("net");
const port = 7070;
const host = "127.0.0.1";
const fs = require("fs");
const files = "./files";
const server = net.createServer();
const prompt = require("prompt-sync")();
server.listen(port, host, () => {
  console.log("TCP Server is running on port " + port + ".");
});

let sockets = [];
let archivos = [];
let ready = [];

server.on("connection", function (sock) {
  console.log("CONNECTED: " + sock.remoteAddress + ":" + sock.remotePort);
  sock.setEncoding("utf8");
  sockets.push(sock);
  bool = false;
  sock.on("data", function (data) {
    console.log(data);
    if (data === "Ready") {
      ready.push(sock);
    }

    if (archivos.includes(data) && bool) {
      console.log("OHHHH SI");
    }

    /*
    console.log("DATA " + sock.remoteAddress + ": " + data);
    // Write the data back to all the connected, the client will receive it as data from the server
    sockets.forEach(function (sock, index, array) {
      var fileStream = fs.createReadStream("./test.txt");
      fileStream.on("error", function (err) {
        console.log(err);
      });

      fileStream.on("open", function () {
        fileStream.pipe(sock);
      });

      sock.write(
        sock.remoteAddress + ":" + sock.remotePort + " hola " + data + "\n"
      );
    });
    */
  });

  // Add a 'close' event handler to this instance of socket
  sock.on("close", function (data) {
    let index = sockets.findIndex(function (o) {
      return (
        o.remoteAddress === sock.remoteAddress &&
        o.remotePort === sock.remotePort
      );
    });
    if (index !== -1) sockets.splice(index, 1);
    console.log("CLOSED: " + sock.remoteAddress + " " + sock.remotePort);
  });
});

/*
msj = "Presione e para enviar un archivo";
while (true) {
  
  const e = prompt(msj + "\n");
  if (e === "e") {
    ans = fs.readdirSync(files);
    console.log(
      "Seleccione uno de los siguientes archivos para enviar a los clientes: "
    );
    ans.map((index, archivo) => {
      console.log(archivo + ". " + index.toString());
    });
    msj = "ingrese el número del archivo que se va a enviar:";
  } else if (e === null) {
    break;
  } else {
    console.log(e);
    console.log(ready);
    // Manda un mensaje a todas las conecciones que estén listas
    ready.map((connection) => {
      connection.push("test");
    });
  }
   */
}
