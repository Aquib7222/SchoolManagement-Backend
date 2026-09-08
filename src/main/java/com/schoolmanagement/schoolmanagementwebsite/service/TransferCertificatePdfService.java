// package com.schoolmanagement.schoolmanagementwebsite.service;

// import com.lowagie.text.*;
// import com.lowagie.text.pdf.*;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
// import com.schoolmanagement.schoolmanagementwebsite.entity.TransferCertificate;
// import org.springframework.stereotype.Service;

// import java.io.ByteArrayOutputStream;

// @Service
// public class TransferCertificatePdfService {

//     public byte[] generatePdf(TransferCertificate tc) {

//         ByteArrayOutputStream outputStream =
//                 new ByteArrayOutputStream();

//         Document document = new Document(
//                 PageSize.A4,
//                 40,
//                 40,
//                 40,
//                 40
//         );

//         try {

//             PdfWriter.getInstance(
//                     document,
//                     outputStream
//             );

//             document.open();

//             Student student = tc.getStudent();

//             // -----------------------------
//             // SCHOOL HEADER
//             // -----------------------------

//             Paragraph schoolName = new Paragraph();

//             schoolName.add(
//                     new Chunk(
//                             tc.getSchool().getSchoolName(),
//                             new Font(
//                                     Font.HELVETICA,
//                                     18,
//                                     Font.BOLD
//                             )
//                     )
//             );

//             schoolName.setAlignment(
//                     Element.ALIGN_CENTER
//             );

//             document.add(schoolName);

//             Paragraph schoolAddress =
//                     new Paragraph(
//                             buildSchoolAddress(tc),
//                             new Font(
//                                     Font.HELVETICA,
//                                     9
//                             )
//                     );

//             schoolAddress.setAlignment(
//                     Element.ALIGN_CENTER
//             );

//             document.add(schoolAddress);

//             document.add(
//                     new Paragraph(" ")
//             );

//             // -----------------------------
//             // TITLE
//             // -----------------------------

//             Paragraph title =
//                     new Paragraph(
//                             "TRANSFER CERTIFICATE",
//                             new Font(
//                                     Font.HELVETICA,
//                                     16,
//                                     Font.BOLD
//                             )
//                     );

//             title.setAlignment(
//                     Element.ALIGN_CENTER
//             );

//             document.add(title);

//             document.add(
//                     new Paragraph(" ")
//             );

//             // -----------------------------
//             // TC NUMBER
//             // -----------------------------

//             PdfPTable tcInfo =
//                     new PdfPTable(2);

//             tcInfo.setWidthPercentage(100);

//             tcInfo.addCell(
//                     createCell(
//                             "TC Number",
//                             true
//                     )
//             );

//             tcInfo.addCell(
//                     createCell(
//                             tc.getTcNumber(),
//                             false
//                     )
//             );

//             tcInfo.addCell(
//                     createCell(
//                             "Date of Issue",
//                             true
//                     )
//             );

//             tcInfo.addCell(
//                     createCell(
//                             String.valueOf(
//                                     tc.getDateOfLeaving()
//                             ),
//                             false
//                     )
//             );

//             document.add(tcInfo);

//             document.add(
//                     new Paragraph(" ")
//             );

//             // -----------------------------
//             // STUDENT DETAILS
//             // -----------------------------

//             PdfPTable table =
//                     new PdfPTable(2);

//             table.setWidthPercentage(100);

//             addRow(
//                     table,
//                     "Admission Number",
//                     student.getAdmissionNumber()
//             );

//             addRow(
//                     table,
//                     "Student Name",
//                     getStudentName(student)
//             );

//             addRow(
//                     table,
//                     "Date of Birth",
//                     student.getDob()
//             );

//             addRow(
//                     table,
//                     "Gender",
//                     student.getGender()
//             );

//             addRow(
//                     table,
//                     "Father's Name",
//                     student.getFatherName()
//             );

//             addRow(
//                     table,
//                     "Mother's Name",
//                     student.getMotherName()
//             );

//             addRow(
//                     table,
//                     "Last Class Attended",
//                     tc.getLastClass()
//             );

//             addRow(
//                     table,
//                     "Section",
//                     tc.getLastSection() != null
//                             ? tc.getLastSection().name()
//                             : "-"
//             );

//             addRow(
//                     table,
//                     "Date of Leaving",
//                     String.valueOf(
//                             tc.getDateOfLeaving()
//                     )
//             );

//             addRow(
//                     table,
//                     "Reason for Leaving",
//                     tc.getReasonForLeaving()
//             );

//             addRow(
//                     table,
//                     "Conduct",
//                     tc.getConduct()
//             );

//             addRow(
//                     table,
//                     "Remarks",
//                     tc.getRemarks()
//             );

//             document.add(table);

//             document.add(
//                     new Paragraph(" ")
//             );

//             document.add(
//                     new Paragraph(" ")
//             );

//             // -----------------------------
//             // DECLARATION
//             // -----------------------------

//             Paragraph declaration =
//                     new Paragraph(
//                             "This is to certify that the above mentioned "
//                             + "student was a bonafide student of the school "
//                             + "and has been issued this Transfer Certificate "
//                             + "as per school records.",
//                             new Font(
//                                     Font.HELVETICA,
//                                     10
//                             )
//                     );

//             declaration.setAlignment(
//                     Element.ALIGN_JUSTIFIED
//             );

//             document.add(declaration);

//             document.add(
//                     new Paragraph("\n\n\n")
//             );

//             // -----------------------------
//             // SIGNATURE
//             // -----------------------------

//             PdfPTable signatures =
//                     new PdfPTable(3);

//             signatures.setWidthPercentage(100);

//             signatures.addCell(
//                     signatureCell("Class Teacher")
//             );

//             signatures.addCell(
//                     signatureCell("School Seal")
//             );

//             signatures.addCell(
//                     signatureCell("Principal")
//             );

//             document.add(signatures);

//             document.close();

//             return outputStream.toByteArray();

//         } catch (Exception e) {

//             throw new RuntimeException(
//                     "Failed to generate Transfer Certificate PDF",
//                     e
//             );
//         }
//     }

//     private void addRow(
//             PdfPTable table,
//             String label,
//             String value
//     ) {

//         table.addCell(
//                 createCell(label, true)
//         );

//         table.addCell(
//                 createCell(
//                         value != null ? value : "-",
//                         false
//                 )
//         );
//     }

//     private PdfPCell createCell(
//             String text,
//             boolean bold
//     ) {

//         Font font = new Font(
//                 Font.HELVETICA,
//                 9,
//                 bold
//                         ? Font.BOLD
//                         : Font.NORMAL
//         );

//         PdfPCell cell =
//                 new PdfPCell(
//                         new Phrase(
//                                 text != null
//                                         ? text
//                                         : "-",
//                                 font
//                         )
//                 );

//         cell.setPadding(8);

//         return cell;
//     }

//     private PdfPCell signatureCell(
//             String text
//     ) {

//         PdfPCell cell =
//                 new PdfPCell(
//                         new Phrase(
//                                 "\n\n\n" + text,
//                                 new Font(
//                                         Font.HELVETICA,
//                                         9,
//                                         Font.BOLD
//                                 )
//                         )
//                 );

//         cell.setHorizontalAlignment(
//                 Element.ALIGN_CENTER
//         );

//         cell.setBorder(
//                 Rectangle.NO_BORDER
//         );

//         return cell;
//     }

//     private String getStudentName(
//             Student student
//     ) {

//         return String.join(
//                 " ",
//                 student.getFirstName() != null
//                         ? student.getFirstName()
//                         : "",
//                 student.getMiddleName() != null
//                         ? student.getMiddleName()
//                         : "",
//                 student.getLastName() != null
//                         ? student.getLastName()
//                         : ""
//         ).trim();
//     }

//     private String buildSchoolAddress(
//             TransferCertificate tc
//     ) {

//         if (tc.getSchool() == null) {
//             return "";
//         }

//         return tc.getSchool().getAddress() != null
//                 ? tc.getSchool().getAddress()
//                 : "";
//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.entity.TransferCertificate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class TransferCertificatePdfService {

    public byte[] generatePdf(TransferCertificate tc) {

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        Document document = new Document(
                PageSize.A4,
                40,
                40,
                40,
                40
        );

        try {

            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.open();

            Student student = tc.getStudent();

            // =====================================================
            // SCHOOL HEADER
            // =====================================================

            Paragraph schoolName = new Paragraph();

            schoolName.add(
                    new Chunk(
                            safe(tc.getSchool().getSchoolName()),
                            new Font(
                                    Font.HELVETICA,
                                    18,
                                    Font.BOLD
                            )
                    )
            );

            schoolName.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(schoolName);

            Paragraph schoolAddress =
                    new Paragraph(
                            buildSchoolAddress(tc),
                            new Font(
                                    Font.HELVETICA,
                                    9
                            )
                    );

            schoolAddress.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(schoolAddress);

            document.add(new Paragraph(" "));

            // =====================================================
            // TITLE
            // =====================================================

            Paragraph title =
                    new Paragraph(
                            "TRANSFER CERTIFICATE",
                            new Font(
                                    Font.HELVETICA,
                                    16,
                                    Font.BOLD
                            )
                    );

            title.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(title);

            document.add(new Paragraph(" "));

            // =====================================================
            // TC INFORMATION
            // =====================================================

            PdfPTable tcInfo =
                    new PdfPTable(2);

            tcInfo.setWidthPercentage(100);

            tcInfo.setWidths(new float[]{35, 65});

            tcInfo.addCell(
                    createCell(
                            "TC Number",
                            true
                    )
            );

            tcInfo.addCell(
                    createCell(
                            tc.getTcNumber(),
                            false
                    )
            );

            tcInfo.addCell(
                    createCell(
                            "Date of Issue",
                            true
                    )
            );

            tcInfo.addCell(
                    createCell(
                            tc.getDateOfLeaving() != null
                                    ? tc.getDateOfLeaving().toString()
                                    : "-",
                            false
                    )
            );

            document.add(tcInfo);

            document.add(new Paragraph(" "));

            // =====================================================
            // STUDENT DETAILS
            // =====================================================

            PdfPTable table =
                    new PdfPTable(2);

            table.setWidthPercentage(100);

            table.setWidths(new float[]{35, 65});

            addRow(
                    table,
                    "Admission Number",
                    student.getAdmissionNumber()
            );

            addRow(
                    table,
                    "Student Name",
                    getStudentName(student)
            );

            addRow(
                    table,
                    "Date of Birth",
                    student.getDob()
            );

            addRow(
                    table,
                    "Gender",
                    student.getGender()
            );

            addRow(
                    table,
                    "Father's Name",
                    student.getFatherName()
            );

            addRow(
                    table,
                    "Mother's Name",
                    student.getMotherName()
            );

            addRow(
                    table,
                    "Last Class Attended",
                    tc.getLastClass()
            );

            addRow(
                    table,
                    "Section",
                    tc.getLastSection() != null
                            ? tc.getLastSection().name()
                            : "-"
            );

            addRow(
                    table,
                    "Date of Leaving",
                    tc.getDateOfLeaving() != null
                            ? tc.getDateOfLeaving().toString()
                            : "-"
            );

            addRow(
                    table,
                    "Reason for Leaving",
                    tc.getReasonForLeaving()
            );

            addRow(
                    table,
                    "Conduct",
                    tc.getConduct()
            );

            addRow(
                    table,
                    "Remarks",
                    tc.getRemarks()
            );

            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // =====================================================
            // DECLARATION
            // =====================================================

            Paragraph declaration =
                    new Paragraph(
                            "This is to certify that the above mentioned "
                            + "student was a bonafide student of the school "
                            + "and has been issued this Transfer Certificate "
                            + "as per school records.",
                            new Font(
                                    Font.HELVETICA,
                                    10
                            )
                    );

            declaration.setAlignment(
                    Element.ALIGN_JUSTIFIED
            );

            document.add(declaration);

            document.add(
                    new Paragraph("\n\n\n")
            );

            // =====================================================
            // SIGNATURE
            // =====================================================

            PdfPTable signatures =
                    new PdfPTable(3);

            signatures.setWidthPercentage(100);

            signatures.addCell(
                    signatureCell("Class Teacher")
            );

            signatures.addCell(
                    signatureCell("School Seal")
            );

            signatures.addCell(
                    signatureCell("Principal")
            );

            document.add(signatures);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to generate Transfer Certificate PDF",
                    e
            );
        }
    }

    // =========================================================
    // ADD TABLE ROW
    // =========================================================

    private void addRow(
            PdfPTable table,
            String label,
            String value
    ) {

        table.addCell(
                createCell(
                        label,
                        true
                )
        );

        table.addCell(
                createCell(
                        value != null
                                ? value
                                : "-",
                        false
                )
        );
    }

    // =========================================================
    // CREATE CELL
    // =========================================================

    private PdfPCell createCell(
            String text,
            boolean bold
    ) {

        Font font = new Font(
                Font.HELVETICA,
                9,
                bold
                        ? Font.BOLD
                        : Font.NORMAL
        );

        PdfPCell cell =
                new PdfPCell(
                        new Phrase(
                                text != null
                                        ? text
                                        : "-",
                                font
                        )
                );

        cell.setPadding(8);

        return cell;
    }

    // =========================================================
    // SIGNATURE CELL
    // =========================================================

    private PdfPCell signatureCell(
            String text
    ) {

        PdfPCell cell =
                new PdfPCell(
                        new Phrase(
                                "\n\n\n" + text,
                                new Font(
                                        Font.HELVETICA,
                                        9,
                                        Font.BOLD
                                )
                        )
                );

        cell.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        cell.setBorder(
                Rectangle.NO_BORDER
        );

        return cell;
    }

    // =========================================================
    // STUDENT NAME
    // =========================================================

    private String getStudentName(
            Student student
    ) {

        String firstName =
                student.getFirstName() != null
                        ? student.getFirstName()
                        : "";

        String middleName =
                student.getMiddleName() != null
                        ? student.getMiddleName()
                        : "";

        String lastName =
                student.getLastName() != null
                        ? student.getLastName()
                        : "";

        return (
                firstName
                        + " "
                        + middleName
                        + " "
                        + lastName
        )
                .trim()
                .replaceAll("\\s+", " ");
    }

    // =========================================================
    // SCHOOL ADDRESS
    // =========================================================

    private String buildSchoolAddress(
            TransferCertificate tc
    ) {

        if (tc.getSchool() == null) {
            return "";
        }

        StringBuilder address =
                new StringBuilder();

        if (tc.getSchool().getAddressLine1() != null &&
                !tc.getSchool().getAddressLine1().isBlank()) {

            address.append(
                    tc.getSchool().getAddressLine1()
            );
        }

        if (tc.getSchool().getAddressLine2() != null &&
                !tc.getSchool().getAddressLine2().isBlank()) {

            appendWithComma(
                    address,
                    tc.getSchool().getAddressLine2()
            );
        }

        if (tc.getSchool().getCity() != null &&
                !tc.getSchool().getCity().isBlank()) {

            appendWithComma(
                    address,
                    tc.getSchool().getCity()
            );
        }

        if (tc.getSchool().getState() != null &&
                !tc.getSchool().getState().isBlank()) {

            appendWithComma(
                    address,
                    tc.getSchool().getState()
            );
        }

        if (tc.getSchool().getCountry() != null &&
                !tc.getSchool().getCountry().isBlank()) {

            appendWithComma(
                    address,
                    tc.getSchool().getCountry()
            );
        }

        if (tc.getSchool().getPincode() != null &&
                !tc.getSchool().getPincode().isBlank()) {

            appendWithComma(
                    address,
                    tc.getSchool().getPincode()
            );
        }

        return address.toString();
    }

    private void appendWithComma(
            StringBuilder builder,
            String value
    ) {

        if (builder.length() > 0) {
            builder.append(", ");
        }

        builder.append(value);
    }

    // =========================================================
    // SAFE STRING
    // =========================================================

    private String safe(String value) {

        return value != null
                ? value
                : "-";
    }
}