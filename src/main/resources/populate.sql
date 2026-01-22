INSERT INTO resume (uuid, full_name) VALUES
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'Name1'),
  ('a97b3ac3-3817-4c3f-8a5f-178497311f1d', 'Name2'),
  ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'Name3');
  
INSERT INTO contact (resume_uuid, type, value) VALUES
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'PHONE', 'phone'),
  ('a97b3ac3-3817-4c3f-8a5f-178497311f1d', 'SKYPE', 'skype'),
  ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'MAIL', 'mail'),
  ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'LINKEDIN', 'linkedin'),
  ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'GITHUB', 'github'),
  ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'STACKOVERFLOW', 'stackoverflow'),
  ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'HOME', 'home');
  
INSERT INTO section (resume_uuid, type, value) VALUES
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'OBJECTIVE','objective'),
  ('a97b3ac3-3817-4c3f-8a5f-178497311f1d', 'PERSONAL', 'personal'),
  ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'ACHIEVEMENT', 'achievement\nachievement\nachievement\nachievement\nachievement'),
  ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'QUALIFICATIONS', 'qualifications\nqualifications\nqualifications\nqualifications\nqualifications');