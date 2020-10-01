const net = require("net");
const port = 7070;
const host = "127.0.0.1";
const fs = require("fs");

const server = net.createServer();
server.listen(port, host, () => {
  console.log("TCP Server is running on port " + port + ".");
});

let sockets = [];

server.on("connection", function (sock) {
  console.log("CONNECTED: " + sock.remoteAddress + ":" + sock.remotePort);
  sockets.push(sock);

  sock.on("data", function (data) {
    console.log(data);
    chunk += data.toString(); // Add string on the end of the variable 'chunk'
    d_index = chunk.indexOf(";"); // Find the delimiter

    // While loop to keep going until no delimiter can be found
    while (d_index > -1) {
      try {
        string = chunk.substring(0, d_index); // Create string up until the delimiter
        console.log(string);
      } catch {
        console.log("error");
      }
      chunk = chunk.substring(d_index + 1); // Cuts off the processed chunk
      d_index = chunk.indexOf(";"); // Find the new delimiter
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
