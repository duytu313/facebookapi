const express = require("express");
const mongoose = require("mongoose");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const multer = require("multer");
const cors = require("cors");
const bodyParser = require("body-parser");

const app = express();
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Cho phép client truy cập ảnh trong thư mục uploads/
app.use("/uploads", express.static("uploads"));

// ================== MONGODB CONNECT ==================
mongoose
  .connect("mongodb://127.0.0.1:27017/facebook_clone", {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => console.log("✅ MongoDB connected"))
  .catch((err) => console.error(err));

// ================== SCHEMAS ==================
const UserSchema = new mongoose.Schema({
  username: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  email: { type: String, required: true, unique: true },
});
const User = mongoose.model("User", UserSchema);

const PostSchema = new mongoose.Schema({
  userId: { type: String, required: true },
  content: { type: String, required: true },
  image: { type: String, default: null },
  createdAt: { type: Date, default: Date.now },
});
const Post = mongoose.model("Post", PostSchema);

const LikeSchema = new mongoose.Schema({
  postId: { type: String, required: true },
  userId: { type: String, required: true },
  createdAt: { type: Date, default: Date.now },
});
const Like = mongoose.model("Like", LikeSchema);

const CommentSchema = new mongoose.Schema({
  postId: { type: String, required: true },
  userId: { type: String, required: true },
  comment: { type: String, required: true },
  createdAt: { type: Date, default: Date.now },
});
const Comment = mongoose.model("Comment", CommentSchema);

// ================== MULTER UPLOAD ==================
const storage = multer.diskStorage({
  destination: (req, file, cb) => cb(null, "uploads/"),
  filename: (req, file, cb) => cb(null, Date.now() + "-" + file.originalname),
});
const upload = multer({ storage });

// ================== API ==================

// ----- REGISTER -----
app.post("/register", async (req, res) => {
  try {
    const { username, password, email } = req.body;
    if (!username || !password || !email)
      return res.status(400).json({ success: false, message: "Thiếu dữ liệu" });

    const exist = await User.findOne({ $or: [{ username }, { email }] });
    if (exist) return res.json({ success: false, message: "User đã tồn tại" });

    const hashed = await bcrypt.hash(password, 10);
    const newUser = new User({ username, password: hashed, email });
    await newUser.save();

    res.json({
      success: true,
      message: "Đăng ký thành công",
      userId: newUser._id,
    });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, message: "Lỗi server" });
  }
});

// ----- LOGIN -----
app.post("/login", async (req, res) => {
  try {
    const { usernameOrEmail, password } = req.body;
    if (!usernameOrEmail || !password)
      return res.status(400).json({ success: false, message: "Thiếu dữ liệu" });

    const user = await User.findOne({
      $or: [{ username: usernameOrEmail }, { email: usernameOrEmail }],
    });
    if (!user) return res.json({ success: false, message: "Sai tài khoản" });

    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch) return res.json({ success: false, message: "Sai mật khẩu" });

    const token = jwt.sign({ id: user._id }, "secret123", { expiresIn: "7d" });
    res.json({
      success: true,
      message: "Đăng nhập thành công",
      userId: user._id,
      token,
    });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, message: "Lỗi server" });
  }
});

// ----- CREATE POST -----
app.post("/createPost", upload.single("image"), async (req, res) => {
  try {
    const { userId, content } = req.body;
    if (!userId || !content)
      return res.status(400).json({ success: false, message: "Thiếu dữ liệu" });

    const image = req.file ? "/uploads/" + req.file.filename : null;
    const newPost = new Post({ userId, content, image });
    await newPost.save();

    res.json({ success: true, message: "Đăng bài thành công", post: newPost });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, message: "Lỗi server" });
  }
});

// ----- GET POSTS -----
app.get("/posts", async (req, res) => {
  try {
    const posts = await Post.find().sort({ createdAt: -1 });
    res.json({ success: true, posts });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, message: "Lỗi server" });
  }
});

// ----- LIKE POST -----
app.post("/likePost", async (req, res) => {
  try {
    const { postId, userId } = req.body;
    if (!postId || !userId)
      return res.status(400).json({ success: false, message: "Thiếu dữ liệu" });

    const exist = await Like.findOne({ postId, userId });
    if (exist) return res.json({ success: false, message: "Bạn đã like rồi" });

    const newLike = new Like({ postId, userId });
    await newLike.save();
    res.json({ success: true, message: "Like thành công", like: newLike });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, message: "Lỗi server" });
  }
});

// ----- UNLIKE POST -----
app.post("/unlikePost", async (req, res) => {
  try {
    const { postId, userId } = req.body;
    if (!postId || !userId)
      return res.status(400).json({ success: false, message: "Thiếu dữ liệu" });

    const deleted = await Like.findOneAndDelete({ postId, userId });
    if (!deleted)
      return res.json({
        success: false,
        message: "Bạn chưa like bài viết này",
      });

    res.json({ success: true, message: "Bỏ like thành công" });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, message: "Lỗi server" });
  }
});

// ----- COMMENT POST -----
app.post("/commentPost", async (req, res) => {
  try {
    const { postId, userId, comment } = req.body;
    if (!postId || !userId || !comment)
      return res.status(400).json({ success: false, message: "Thiếu dữ liệu" });

    const newComment = new Comment({ postId, userId, comment });
    await newComment.save();
    res.json({ success: true, message: "Đã comment", comment: newComment });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, message: "Lỗi server" });
  }
});

// ----- GET COMMENTS -----
app.get("/comments/:postId", async (req, res) => {
  try {
    const { postId } = req.params;
    const comments = await Comment.find({ postId }).sort({ createdAt: -1 });
    res.json({ success: true, comments });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, message: "Lỗi server" });
  }
});

// ----- GET LIKE COUNT -----
app.get("/likes/:postId", async (req, res) => {
  try {
    const { postId } = req.params;
    const count = await Like.countDocuments({ postId });
    res.json({ success: true, postId, likesCount: count });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, message: "Lỗi server" });
  }
});

// ----- DELETE POST -----
app.delete("/deletePost/:postId", async (req, res) => {
  try {
    const { postId } = req.params;
    const post = await Post.findByIdAndDelete(postId);
    if (!post)
      return res
        .status(404)
        .json({ success: false, message: "Bài viết không tồn tại" });

    await Like.deleteMany({ postId });
    await Comment.deleteMany({ postId });
    res.json({ success: true, message: "Xóa bài viết thành công" });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, message: "Lỗi server" });
  }
});

// ----- DELETE COMMENT -----
app.delete("/deleteComment/:commentId", async (req, res) => {
  try {
    const { commentId } = req.params;
    const comment = await Comment.findByIdAndDelete(commentId);
    if (!comment)
      return res
        .status(404)
        .json({ success: false, message: "Comment không tồn tại" });

    res.json({ success: true, message: "Xóa comment thành công" });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, message: "Lỗi server" });
  }
});

// ================== RUN SERVER ==================
const PORT = 3000;
app.listen(PORT, () =>
  console.log(`🚀 Server chạy tại http://localhost:${PORT}`)
);
