import axios from "axios";

export default defineEventHandler(async (event) => {
  try {
    const res = await axios.get(`http://127.0.0.1:8082/api/v1/car`);
    return res.data;
  } catch (error) {
    console.log(error);
  }
});
