import axios from "axios";

export default defineEventHandler(async (event) => {
  try {
    const { id } = event.context.params;
    const vehicleID = Number(id);
    if (isNaN(vehicleID)) {
      return "Missing id!";
    }
    const body = await readBody(event);
    const { model, year, color, carCondition, file, type, price } = body;
    const fd = new FormData();
    fd.append("model", model);
    fd.append("year", year);
    fd.append("color", color);
    fd.append("carCondition", carCondition);
    fd.append("file", file);
    fd.append("type", type);
    fd.append("price", price);
    const res = await axios.put(
      `http://127.0.0.1:8082/api/v1/car/${vehicleID}`,fd);
    return res.data;
  } catch (error) {
    console.log(error);
  }
});
