const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const { v4: uuidv4 } = require("uuid");

const app = express();
const PORT = 3000;

app.use(cors());
app.use(bodyParser.json());

let users = [];

// Register
app.post("/register", (req, res) => {
  const { username, email, password } = req.body;
  if (!username || !email || !password) {
    return res.json({ success: false, message: "Nhập đầy đủ thông tin" });
  }
  const exist = users.find((u) => u.username === username || u.email === email);
  if (exist)
    return res.json({
      success: false,
      message: "Username hoặc email đã tồn tại",
    });

  users.push({ username, email, password });
  return res.json({ success: true, message: "Tạo tài khoản thành công" });
});

// Login (username hoặc email)
app.post("/login", (req, res) => {
  const { usernameOrEmail, password } = req.body;
  const user = users.find(
    (u) =>
      (u.username === usernameOrEmail || u.email === usernameOrEmail) &&
      u.password === password
  );

  if (user) {
    return res.json({
      success: true,
      token: uuidv4(),
      message: "Đăng nhập thành công",
    });
  } else {
    return res.json({
      success: false,
      token: null,
      message: "Sai username/email hoặc mật khẩu",
    });
  }
});

app.listen(PORT, () => console.log(`Server chạy tại http://localhost:${PORT}`));
