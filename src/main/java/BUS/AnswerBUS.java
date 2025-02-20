    package BUS;

    import DAO.AnswerDAO;
    import DTO.AnswerDTO;

    import java.util.ArrayList;
    import java.util.List;

    public class AnswerBUS {
        private static AnswerBUS instance;
        private final AnswerDAO answerDAO;

        private AnswerBUS() {
            this.answerDAO = AnswerDAO.getInstance();
        }
        public boolean addAnswer(AnswerDTO answer) {
            if (answer == null || answer.getQID() <= 0) {
                System.out.println("Lỗi: ID câu hỏi không hợp lệ!");
                return false;
            }

            boolean success = AnswerDAO.getInstance().add(answer) > 0;
            if (success) {
                System.out.println("Thêm câu trả lời thành công vào database!");
            } else {
                System.out.println("Thêm câu trả lời thất bại!");
            }
            return success;
        }


        public static AnswerBUS getInstance() {
            if (instance == null) {
                synchronized (AnswerBUS.class) {
                    if (instance == null) {
                        instance = new AnswerBUS();
                    }
                }
            }
            return instance;
        }
        /**
         * ✅ Cập nhật trạng thái câu trả lời theo ID câu hỏi
         */
        public boolean setAnswersStatusByQuestionID(int questionID, int status) {
            if (questionID <= 0) {
                System.out.println("⚠️ Lỗi: ID câu hỏi không hợp lệ!");
                return false;
            }

            boolean success = answerDAO.updateAnswersStatusByQuestionID(questionID, status);
            if (success) {
                System.out.println("✅ Đã cập nhật trạng thái câu trả lời thành công! (qID: " + questionID + ", awStatus: " + status + ")");
            } else {
                System.out.println("❌ Cập nhật trạng thái thất bại!");
            }
            return success;
        }

        /**
         * ✅ Thêm danh sách câu trả lời vào database
         */
        public boolean addAnswers(List<AnswerDTO> answers) {
            if (answers == null || answers.isEmpty()) {
                System.out.println("Lỗi: Danh sách câu trả lời rỗng!");
                return false;
            }

            int correctCount = 0;
            for (AnswerDTO answer : answers) {
                if (answer.getIsRight() == 1) {
                    correctCount++;
                }
            }

            if (correctCount != 1) {
                System.out.println("Lỗi: Cần có đúng 1 đáp án đúng!");
                return false;
            }

            AnswerDAO.getInstance().addAnswers(answers);
            System.out.println("Thêm danh sách câu trả lời thành công!");
            return true;
        }


        /**
         * ✅ Cập nhật câu trả lời
         */
        public boolean updateAnswer(AnswerDTO answer) {
            if (answer == null || answer.getAwID() <= 0) {
                System.out.println("⚠️ Lỗi: ID câu trả lời không hợp lệ!");
                return false;
            }

            boolean success = AnswerDAO.getInstance().updateAnswer(answer);
            if (success) {
                System.out.println("✅ Cập nhật câu trả lời thành công!");
            } else {
                System.out.println("❌ Cập nhật thất bại!");
            }
            return success;
        }
        public boolean deleteAnswersByQuestionID(int questionID) {
            if (questionID <= 0) {
                System.out.println("⚠️ Lỗi: ID câu hỏi không hợp lệ!");
                return false;
            }

            boolean success = AnswerDAO.getInstance().deleteAnswersByQuestionID(questionID);
            if (success) {
                System.out.println("✅ Đã xóa tất cả câu trả lời của câu hỏi ID: " + questionID);
            } else {
                System.out.println("❌ Xóa câu trả lời thất bại!");
            }
            return success;
        }


        /**
         * ✅ Xóa câu trả lời theo ID
         */
        public boolean deleteAnswer(int awID) {
            if (awID <= 0) {
                System.out.println("Lỗi: ID câu trả lời không hợp lệ!");
                return false;
            }

            boolean success = answerDAO.deleteAnswer(awID);
            if (success) {
                System.out.println("Xóa câu trả lời thành công!");
            } else {
                System.out.println("Xóa thất bại!");
            }
            return success;
        }

        /**
         * ✅ Lấy danh sách câu trả lời theo `qID`
         */
        public List<AnswerDTO> getAnswersByQuestionID(int questionID) {
            return AnswerDAO.getInstance().getAnswersByQuestionID(questionID);
        }


    }
