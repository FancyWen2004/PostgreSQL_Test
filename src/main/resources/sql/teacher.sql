-- 创建教师表
CREATE TABLE IF NOT EXISTS teacher (
    id BIGSERIAL PRIMARY KEY,
    teacher_no VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    age INTEGER,
    title VARCHAR(50),
    subject VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(20),
    status SMALLINT DEFAULT 1,
    CONSTRAINT chk_age CHECK (age > 0 AND age < 100),
    CONSTRAINT chk_status CHECK (status IN (0, 1))
);

-- 创建索引
CREATE INDEX idx_teacher_title ON teacher(title);
CREATE INDEX idx_teacher_subject ON teacher(subject);
CREATE INDEX idx_teacher_email ON teacher(email);

-- 添加注释
COMMENT ON TABLE teacher IS '教师信息表';
COMMENT ON COLUMN teacher.id IS '主键ID';
COMMENT ON COLUMN teacher.teacher_no IS '教师工号';
COMMENT ON COLUMN teacher.name IS '教师姓名';
COMMENT ON COLUMN teacher.age IS '年龄';
COMMENT ON COLUMN teacher.title IS '职称';
COMMENT ON COLUMN teacher.subject IS '所教学科';
COMMENT ON COLUMN teacher.email IS '邮箱';
COMMENT ON COLUMN teacher.phone IS '联系电话';
COMMENT ON COLUMN teacher.status IS '状态：1-在职 0-离职';