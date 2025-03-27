import { useState } from "react";
import axios from "axios";

function App() {
  const [file, setFile] = useState(null);
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const uploadFile = async () => {
    if (!file) {
      alert("Por favor, selecciona un archivo.");
      return;
    }

    setLoading(true);
    setError(null);

    try {
      // Convertir el archivo a Base64
      const base64 = await convertFileToBase64(file);

      // Enviar a la API
      const response = await axios.post(
        "/documents/upload",
        { base64Source: base64 },
        { headers: { "Content-Type": "application/json" } }
      );

      // Guardar la respuesta
      setData(response.data);
    } catch (error) {
      if (error.response) {
        setError(`Error: ${error.response.data}`);
      } else {
        setError("Error al procesar el documento.");
      }
      console.error("Error:", error);
    } finally {
      setLoading(false);
    }
  };

  // Convertir archivo a Base64 y eliminar el prefijo "data:image/png;base64,"
  const convertFileToBase64 = (file) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        const base64String = reader.result.split(",")[1];
        resolve(base64String);
      };
      reader.onerror = (error) => reject(error);
    });
  };

  return (
    <div>
      <h1>Extractor de texto de cedulas Colombianas</h1>
      <input type="file" onChange={handleFileChange} />
      <button onClick={uploadFile} disabled={loading}>
        {loading ? "Subiendo..." : "Subir"}
      </button>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {data && (
        <div>
          <h2>Datos extraídos:</h2>
          <table border="1" cellPadding="10" style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead>
              <tr>
                <th>Campo</th>
                <th>Valor</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Nombre</td>
                <td>{data.firstName}</td>
              </tr>
              <tr>
                <td>Apellidos</td>
                <td>{data.lastName}</td>
              </tr>
              <tr>
                <td>Número de Documento</td>
                <td>{data.documentNumber}</td>
              </tr>
              <tr>
                <td>Fecha de Nacimiento</td>
                <td>{data.dateOfBirth}</td>
              </tr>
              <tr>
                <td>Lugar de Nacimiento</td>
                <td>{data.lugarDeNacimiento}</td>
              </tr>
              <tr>
                <td>Estatura</td>
                <td>{data.estatura}</td>
              </tr>
              <tr>
                <td>RH</td>
                <td>{data.rh}</td>
              </tr>
              <tr>
                <td>Sexo</td>
                <td>{data.sexo}</td>
              </tr>
              <tr>
                <td>Fecha de Expedición</td>
                <td>{data.fechaDeExpedicion}</td>
              </tr>
              <tr>
                <td>Lugar de Expedición</td>
                <td>{data.lugarDeExpedicion}</td>
              </tr>
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default App;
