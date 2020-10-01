const net = require("net");
fs = require("fs");
let socket;
const client = new net.Socket();
const port = 3000;
const host = "54.234.96.150";

client.connect(port, host, function () {
  console.log("Connected");
  client.write("Hello From Client " + client.address().address);
  client.write("Ready");

  /*
  socket = host ? net.connect(port, host) : net.connect(3000);
  let ostream = fs.createWriteStream("./");
  let date = new Date(),
    size = 0,
    elapsed;
  socket.on("data", (chunk) => {
    size += chunk.length;
    elapsed = new Date() - date;
    socket.write(
      `\r${(size / (1024 * 1024)).toFixed(
        2
      )} MB of data was sent. Total elapsed time is ${elapsed / 1000} s`
    );
    process.stdout.write(
      `\r${(size / (1024 * 1024)).toFixed(
        2
      )} MB of data was sent. Total elapsed time is ${elapsed / 1000} s`
    );
    ostream.write(chunk);
  });
  socket.on("end", () => {
    console.log(
      `\nFinished getting file. speed was: ${(
        size /
        (1024 * 1024) /
        (elapsed / 1000)
      ).toFixed(2)} MB/s`
    );
    process.exit();
  });
  */
});
client.on("data", function (data) {
  console.log("Server Says : " + data);
  var file = window.prompt("Ingresa el archivo: ");
  client.write(file);
  //  let ostream = fs.createWriteStream("./" + file);
});
client.on("close", function () {
  console.log("Connection closed");
});
